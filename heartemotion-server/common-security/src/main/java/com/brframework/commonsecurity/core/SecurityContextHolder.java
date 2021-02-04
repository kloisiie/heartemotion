package com.brframework.commonsecurity.core;

import com.brframework.commonweb.core.SpringContext;
import com.brframework.commonweb.utils.ServletUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @Author xu
 * @Date 2018/2/28 0028 上午 11:37
 * 安全架构上下文持有
 */
public class SecurityContextHolder {

    /** jwt 转换后的用户信息 */
    private final static ThreadLocal<SecurityUserDetails> USER_DETAILS = new ThreadLocal<>();


    public static SecurityUserDetails getUserDetails() {
        return USER_DETAILS.get();
    }

    /**
     * 加载Subject
     * @param userDetailsService
     */
    public static void loadUserDetails(UserDetailsService userDetailsService) {
        String auth = ServletUtils.request().getHeader("Authorization");
        //不存在Authorization
        if(StringUtils.isEmpty(auth)) {
            return;
        }

        SecurityUserDetails user = (SecurityUserDetails) userDetailsService.loadUserByUsername(auth);

        //持有subject
        SecurityContextHolder.setUserDetails(user);

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.getUsername(), null, user.getAuthorities());
        token.setDetails(user);
        //设置security权限
        org.springframework.security.core.context.SecurityContextHolder.getContext().setAuthentication(token);
    }

    /**
     * 加载Subject
     * @param springSecurityBeanName
     */
    public static void loadUserDetails(String springSecurityBeanName) {
        //获取bean
        SecurityUserDetailsService userDetailsService = SpringContext.getBean(springSecurityBeanName);
        loadUserDetails(userDetailsService);
    }

    public static void setUserDetails(SecurityUserDetails userDetails) {
        USER_DETAILS.set(userDetails);
    }

    public static boolean hasRole(String role){
        SecurityUserDetails userDetails = getUserDetails();
        for (GrantedAuthority auth : userDetails.getAuthorities()) {
            if(auth.getAuthority().equals(role)){
                return true;
            }
        }
        return false;
    }

    /**
     * 释放
     */
    public static void destroy(){
        USER_DETAILS.remove();
    }
}
