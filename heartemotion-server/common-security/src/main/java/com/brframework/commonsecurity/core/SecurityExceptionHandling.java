package com.brframework.commonsecurity.core;

import com.brframework.commonsecurity.exception.UnauthorizedTokenException;
import com.brframework.commonweb.json.JSONResult;
import com.brframework.commonweb.utils.RequestResponseUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author xu
 * @Date 2018/2/2 0002 下午 3:04
 * security 访问处理
 */
public class SecurityExceptionHandling implements AuthenticationEntryPoint, AccessDeniedHandler {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        RequestResponseUtils.writerJson(response, JSONResult.error(new UnauthorizedTokenException()).toString());
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, org.springframework.security.access.AccessDeniedException accessDeniedException) throws IOException, ServletException {
        RequestResponseUtils.writerJson(response, JSONResult.error(new UnauthorizedTokenException()).toString());
    }
}
