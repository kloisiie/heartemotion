package cn.bluetech.gragas.aop;

import cn.bluetech.gragas.pojo.client.OptionLogInsertParamDTO;
import cn.bluetech.gragas.service.client.OptionLogService;
import com.brframework.commonsecurity.core.SecurityContextHolder;
import com.brframework.commonweb.utils.IPUtils;
import com.brframework.commonweb.utils.ServletUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
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
public class ApiLogAspect {

    @Autowired
    OptionLogService optionLogService;

    @Before("@annotation(cn.bluetech.gragas.aop.annotation.APILog)")
    public void before() throws Throwable {

        OptionLogInsertParamDTO log = new OptionLogInsertParamDTO();
        if(SecurityContextHolder.getUserDetails() != null){
            log.setClientId(SecurityContextHolder.getUserDetails().getId());
        }

        log.setCallApi(ServletUtils.request().getRequestURI());
        log.setIp(IPUtils.getRequestPublicIp());

        optionLogService.insertOptionLog(log);

    }
}
