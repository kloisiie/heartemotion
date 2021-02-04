package com.brframework.commonweb.core;

import cn.hutool.core.io.IoUtil;
import com.alibaba.fastjson.JSON;
import com.brframework.commonweb.exception.HandleException;
import com.brframework.commonweb.json.JSONResult;
import com.brframework.commonweb.utils.ServletUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.async.WebAsyncUtils;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.Map;

/**
 * @Author xu
 * @Date 2017/12/28 0028 上午 11:43
 * Restful api 全局异常处理
 */
@ControllerAdvice
@Slf4j
public class RestExceptionHandler {

    @ExceptionHandler(Throwable.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public JSONResult allExceptionHandler(Throwable e) {

        if(e instanceof MethodArgumentNotValidException){
            //验证参数输入错误
            return JSONResult.error(new HandleException(
                    ((MethodArgumentNotValidException)e).getBindingResult().getFieldError().getDefaultMessage())
            );
        } else if(e instanceof BindException){
            return JSONResult.error(new HandleException(
                    ((BindException)e).getBindingResult().getFieldError().getDefaultMessage())
            );
        } else if( e instanceof HandleException){
            return JSONResult.error((HandleException) e);
        }

        //系统出现意外情况的错误
        printError(e);

        return JSONResult.error(e);
    }

    /**
     * 系统出现意外情况的错误时整理打印错误信息
     * @param e
     */
    private void printError(Throwable e){
        log.error("请求意外终止，异常产生URI:\n{}\n请留意以下堆栈信息"
                , requestInfo(), e);
    }

    @SneakyThrows
    private String requestInfo(){
        StringBuilder sb = new StringBuilder();
        HttpServletRequest request = ServletUtils.request();
        sb.append("***********************************************************************\n");
        sb.append("-->\t").append(request.getMethod()).append("\t").append(request.getRequestURI()).append("\n");
        sb.append("<============================== header ================================>\n");
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()){
            String header = headerNames.nextElement();
            String value = StringUtils.isNotEmpty(request.getHeader(header)) ?
                    URLDecoder.decode(request.getHeader(header), "UTF-8") : "null";
            sb.append(header).append(":\t").append(value).append("\n");
        }
        sb.append("\n");
        sb.append("<============================== param ================================>\n");
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()){
            String parameter = parameterNames.nextElement();
            String value = StringUtils.isNotEmpty(request.getParameter(parameter)) ?
                    URLDecoder.decode(request.getParameter(parameter), "UTF-8") : "null";
            sb.append(parameter).append(":\t").append(value).append("\n");
        }
        sb.append("\n");
        sb.append("\n");

        if(ServletUtils.request().getAttribute("REQUEST_BODY") != null){
            sb.append(JSON.toJSONString(ServletUtils.request().getAttribute("REQUEST_BODY")));
        }
        sb.append("\n");
        sb.append("***********************************************************************\n");

        return sb.toString();
    }



}
