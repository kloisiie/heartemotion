package com.brframework.commonsecurity;

import com.brframework.commonsecurity.core.SecurityContextHolder;
import com.brframework.commonsecurity.core.SecurityUserDetails;
import com.brframework.commonsecurity.exception.SecurityException;
import com.brframework.commonweb.exception.HandleException;
import com.brframework.commonweb.json.JSONResult;
import com.brframework.commonweb.utils.RequestResponseUtils;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author xu
 * @Date 2018/1/31 0031 下午 4:16
 * Security jwt 过滤器
 */
@Slf4j
public class SecurityFilter extends OncePerRequestFilter {

    private UserDetailsService userDetailsService;

    public SecurityFilter(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {
            SecurityContextHolder.loadUserDetails(userDetailsService);
            filterChain.doFilter(request, response);

        } catch (SecurityException e){
            RequestResponseUtils.writerJson(response, JSONResult.error(e).toString());
        } finally {
            //释放线程变量
            SecurityContextHolder.destroy();
        }


    }
}
