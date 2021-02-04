package com.brframework.commonweb.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * 跨域访问配置
 */
@Configuration
public class CrossConfig {

    private CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        return corsConfiguration;
    }

    /**
     * 跨域过滤器
     * @return
     */
    @Bean
    public FilterRegistrationBean appCORSFilter() {

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", buildConfig()); // 4

        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new CorsFilter(source));//添加过滤器
        registration.addUrlPatterns("/*");//设置过滤路径，/*所有路径
        registration.setName("appCORSFilter");//设置优先级
        registration.setOrder(Integer.MIN_VALUE);//设置优先级

        return registration;
    }

}
