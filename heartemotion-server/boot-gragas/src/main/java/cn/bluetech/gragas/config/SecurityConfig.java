package cn.bluetech.gragas.config;

import com.brframework.commonsecurity.SecurityFilter;
import com.brframework.commonsecurity.core.*;
import com.brframework.commonwebadmin.entity.admin.AdminUser;
import com.brframework.commonwebadmin.service.admin.AdminUserService;
import com.google.common.collect.Lists;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author xu
 * @date 2019/8/19 20:16
 */
@EnableWebSecurity
@Configuration
@Data
public class SecurityConfig {

    @Autowired
    SecurityUserDetailsConfig config;

    @Autowired
    AdminUserService adminUserService;

    @Bean("adminUserDetailsService")
    public SecurityUserDetailsService adminUserDetailsService() {
        return new SecurityUserDetailsService(config.getAdminIss(), config.getAdminExp(),
                config.getAdminSecret(), securitySubject -> {
            Integer userId = Integer.parseInt(securitySubject.getId());
            AdminUser adminUser = adminUserService.accessObject(userId);
            return new SecurityUserDetails(securitySubject.getId(), securitySubject.getUsername(),
                    securitySubject.getPassword(),
                    adminUserService.getAuthorities(userId), adminUser.getState() == 0,
                    securitySubject.getExpand());
        });
    }


    @Configuration
    @Order(1)
    public static class AdminSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {

        @Autowired
        @Qualifier("adminUserDetailsService")
        SecurityUserDetailsService userDetailsService;

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .antMatcher("/admin/access/**")
                    .addFilterBefore(new SecurityFilter(userDetailsService), UsernamePasswordAuthenticationFilter.class)
                    .httpBasic().and()
                    // 基于token，所以不需要session
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                    //使用我们自己的异常处理机制
                    .exceptionHandling()
                    .authenticationEntryPoint(new SecurityExceptionHandling())
                    .accessDeniedHandler(new SecurityExceptionHandling()).and()
                    .csrf().disable()      //禁用csrf攻击防护，因为使用的是jwt所以不存在csrf攻击的威胁
                    .logout().disable()    //禁用security退出功能
                    .formLogin().disable() //禁用security登录功能
                    .headers().disable();
        }
    }

    @Configuration
    @Order(3)
    public static class AnySecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .authorizeRequests().anyRequest().permitAll().and()
                    .httpBasic().and()
                    // 基于token，所以不需要session
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                    .csrf().disable()      //禁用csrf攻击防护，因为使用的是jwt所以不存在csrf攻击的威胁
                    .cors().disable()      //禁用跨域控制
                    .logout().disable()    //禁用security退出功能
                    .formLogin().disable() //禁用security登录功能
                    .headers().disable();
        }
    }

    /**
     *
     * @param
     * @return
     */
    @Bean("apiUserDetailsService")
    public SecurityUserDetailsService apiUserDetailsService() {
        return new SecurityUserDetailsService(config.getApiIss(), config.getApiExp(), config.getApiSecret(),
                new SecurityUserDetailsConverter() {
                    @Override
                    public SecurityUserDetails converter(SecuritySubject securitySubject) {
                        return new SecurityUserDetails(securitySubject.getId(), securitySubject.getUsername(),
                                securitySubject.getPassword(), Lists.newArrayList("ROLE_USER"),
                                true, securitySubject.getExpand());
                    }
                });
    }


    @Configuration
    @Order(2)
    public static class ApiSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {

        @Autowired
        @Qualifier("apiUserDetailsService")
        SecurityUserDetailsService userDetailsService;

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .antMatcher("/api/access/**").authorizeRequests()
                    .antMatchers("/api/access/**").hasRole("USER").and()
                    .addFilterBefore(new SecurityFilter(userDetailsService), UsernamePasswordAuthenticationFilter.class)
                    .httpBasic().and()
                    // 基于token，所以不需要session
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
//                    //使用我们自己的异常处理机制
                    .exceptionHandling()
                    .authenticationEntryPoint(new SecurityExceptionHandling())
                    .accessDeniedHandler(new SecurityExceptionHandling()).and()
                    .csrf().disable()      //禁用csrf攻击防护，因为使用的是jwt所以不存在csrf攻击的威胁
                    .logout().disable()    //禁用security退出功能
                    .formLogin().disable() //禁用security登录功能
                    .headers().disable();
        }
    }

}
