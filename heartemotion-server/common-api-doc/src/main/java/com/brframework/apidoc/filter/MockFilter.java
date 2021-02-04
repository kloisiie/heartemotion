package com.brframework.apidoc.filter;

import com.brframework.apidoc.service.ApiDocService;
import com.brframework.apidoc.utils.PatternMatcher;
import com.brframework.apidoc.utils.ServletPathMatcher;
import com.brframework.commonweb.utils.RequestResponseUtils;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.Set;

/**
 * 处理MOCK请求数据
 * @author xu
 * @date 2020/4/20 15:21
 */
@Component
@WebFilter(urlPatterns = "/*", filterName = "mockFilter")
@Order(Integer.MIN_VALUE)
@Slf4j
public class MockFilter implements Filter {

    public static final String EXCLUSIONS = "/doc/*,*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,*.html";

    @Autowired
    RequestMappingHandlerMapping requestMappingHandlerMapping;
    @Autowired
    ApiDocService apiDocService;

    @Value(value = "${api-doc.mock.enable}")
    boolean enable;

    private Set<String> excludesPattern;
    private PatternMatcher pathMatcher = new ServletPathMatcher();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        excludesPattern = Sets.newHashSet(EXCLUSIONS.split("\\s*,\\s*"));
    }

    private boolean isExclusion(String requestURI) {
        if (excludesPattern == null || requestURI == null) {
            return false;
        }

        for (String pattern : excludesPattern) {
            if (pathMatcher.matches(pattern, requestURI)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if(!enable) {
            //mock未开启
            chain.doFilter(request, response);
            return;
        }

        String requestURI = ((HttpServletRequest) request).getRequestURI();

        //与mock无关的请求全部绕道
        if(isExclusion(requestURI)){
            chain.doFilter(request, response);
            return;
        }

        boolean openMock = apiDocService.isOpenMock(requestURI,
                ((HttpServletRequest) request).getMethod());
        if(!openMock){
            chain.doFilter(request, response);
            return;
        }

        String mockData = apiDocService.mockApi(requestURI,
                ((HttpServletRequest) request).getMethod());
        RequestResponseUtils.writerJson(response, mockData);

    }

    @Override
    public void destroy() {

    }
}
