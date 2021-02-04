package com.brframework.commonwebadmin.config;

import com.brframework.commonweb.core.SwaggerContext;
import com.google.common.collect.Lists;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.List;

/**
 * 管理后台Swagger配置
 * @Author xu
 * @Date 2018/1/3 0003 下午 3:30
 * Swagger api生成
 */
@Configuration
@Data
public class AdminUserSwaggerConfig {

    @Value("${swagger.enable}")
    private boolean enable;
    private String groupName = "admin-user";
    private String packageName = "com.brframework.commonwebadmin.web.admin";
    private String title = "后台用户系统接口文档";
    private String description = "后台用户系统接口文档";
    private String termsOfServiceUrl = "";
    private String version = "1.0";
    @Value("${swagger.host}")
    private String host;
    @Value("${swagger.project.name}")
    private String projectName;
    private String contactName = "beiru";
    private String contactUrl = "https://www.software-br.com";
    private String contactEmail = "";



    @Bean("adminUserDocket")
    public Docket api() {

        List<Parameter> list = Lists.newArrayList();
        ParameterBuilder tokenPar = new ParameterBuilder();
        tokenPar.name("Authorization").description("token").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        list.add(tokenPar.build());

        return SwaggerContext.createDocket(groupName, packageName, apiInfo(), list, enable, host, projectName);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(title)//大标题
                .description(description)//详细描述
                .version(version)//版本
                .termsOfServiceUrl(termsOfServiceUrl)
                .contact(new Contact(contactName, contactUrl, contactEmail))//作者
                .license("The Apache License, Version 2.0")
                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
                .build();
    }

}
