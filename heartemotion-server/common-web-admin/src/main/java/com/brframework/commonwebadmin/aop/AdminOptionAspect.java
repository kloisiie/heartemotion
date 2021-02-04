package com.brframework.commonwebadmin.aop;

import com.brframework.cms2.utils.ControllerUtil;
import com.brframework.commonsecurity.core.SecurityContextHolder;
import com.brframework.commonsecurity.core.SecuritySubject;
import com.brframework.commonsecurity.core.SecurityUserDetails;
import com.brframework.commonweb.exception.HandleException;
import com.brframework.commonweb.utils.IPUtils;
import com.brframework.commonweb.utils.ServletUtils;
import com.brframework.commonwebadmin.entity.admin.AdminOptionLog;
import com.brframework.commonwebadmin.entity.admin.AdminUser;
import com.brframework.commonwebadmin.json.admin.adminuser.LoginParam;
import com.brframework.commonwebadmin.service.admin.AdminOptionLogService;
import com.brframework.commonwebadmin.service.admin.AdminUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.time.LocalDateTime;

/**
 * 管理员操作切面
 * @author xu
 * @date 2019/10/9 14:08
 */
@Component
@Aspect
@Slf4j
@Order(value = Integer.MIN_VALUE)  //确保最早执行
public class AdminOptionAspect {

    @Autowired
    AdminOptionLogService adminOptionLogService;
    @Autowired
    AdminUserService adminUserService;

    @Around("@annotation(com.brframework.commonwebadmin.aop.annotation.AOLog)")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {

        SecurityUserDetails userDetails = SecurityContextHolder.getUserDetails();
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        //生成参数
        StringBuilder param = new StringBuilder();
        for (Object arg : pjp.getArgs()) {
            if(arg instanceof ServletRequest ||
                    arg instanceof ServletResponse ||
                    arg instanceof ModelAndView){
                continue;
            }

            param.append(arg.getClass().getSimpleName());
            param.append("->");
            param.append( arg.toString());
            param.append("\n");
        }

        AdminOptionLog log = new AdminOptionLog();
        log.setCreateDate(LocalDateTime.now());
        log.setStatus("OK");
        log.setCallApi(ServletUtils.request().getRequestURI());
        log.setInfo(ControllerUtil.getTitle(methodSignature.getMethod()));
        log.setIp(IPUtils.getRequestPublicIp());
        log.setParam(param.toString());
        if(userDetails == null){
            for (Object arg : pjp.getArgs()) {
                if(arg instanceof LoginParam){
                    AdminUser byUsername = adminUserService.findByUsername(((LoginParam) arg).getUsername());
                    if(byUsername != null){
                        log.setUserId(byUsername.getId());
                        log.setUsername(byUsername.getUsername());
                    }
                    break;
                }
            }

        } else {
            log.setUserId(Integer.parseInt(userDetails.getId()));
            log.setUsername(userDetails.getUsername());
        }


        //执行业务逻辑
        Object result = null;
        Throwable exception = null;
        try{
            result = pjp.proceed();
        } catch (Throwable e){
            exception = e;
        }

        if(exception != null){
            log.setStatus("ERROR");
            if(exception instanceof HandleException){
                log.setErrorLog(((HandleException) exception).getMsg());
            } else {
                log.setErrorLog(ExceptionUtils.getStackTrace(exception));
            }

        }


        adminOptionLogService.addLog(log);


        if(exception != null){
            //抛出异常
            throw exception;
        } else {
            //返回结果
            return result;
        }

    }
}
