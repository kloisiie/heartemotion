package com.brframework.commondb.config;

import com.querydsl.sql.MySQLTemplates;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by xu
 *
 * query dsl 配置
 *
 * @author xu
 * @date 18-7-26 下午5:31
 */
@Configuration
public class QueryDslConfig {

    @Bean
    public com.querydsl.sql.Configuration configuration(){
        return new com.querydsl.sql.Configuration(MySQLTemplates.DEFAULT);
    }

}
