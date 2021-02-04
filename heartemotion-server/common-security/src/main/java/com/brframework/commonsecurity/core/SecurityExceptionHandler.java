package com.brframework.commonsecurity.core;

import com.brframework.commonsecurity.exception.UnauthorizedTokenException;
import com.brframework.commonweb.exception.HandleException;
import com.brframework.commonweb.json.JSONResult;
import com.brframework.commonweb.utils.ServletUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * security 异常处理
 * @Author xu
 * @Date 2017/12/28 0028 上午 11:43
 */
@ControllerAdvice
@Slf4j
public class SecurityExceptionHandler {

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public JSONResult allExceptionHandler(AccessDeniedException e) {
        log.error("token: {}, 无权限访问，请关注", ServletUtils.request().getHeader("Authorization"));
        return JSONResult.error(new UnauthorizedTokenException());
    }



}
