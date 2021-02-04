package com.brframework.cms2.config;

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
 * CMS Swagger配置
 * @Author xu
 * @Date 2020年11月7日17:14:49
 * Swagger CMS 生成
 */
@Configuration
@Data
public class CmsSwaggerConfig {

    @Value("${swagger.enable}")
    private boolean enable;
    private String groupName = "cms2";
    private String packageName = "com.brframework.cms2.web";
    private String title = "CMS接口文档";
    private String description = "CMS接口文档";
    private String termsOfServiceUrl = "";
    private String version = "1.0";
    @Value("${swagger.host}")
    private String host;
    @Value("${swagger.project.name}")
    private String projectName;

    private String contactName = "beiru";
    private String contactUrl = "https://www.software-br.com";
    private String contactEmail = "";



    @Bean("cmsDocket")
    public Docket api() {

        List<Parameter> list = Lists.newArrayList();
        ParameterBuilder tokenPar = new ParameterBuilder();
        tokenPar.name("Authorization").description("token").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        ParameterBuilder requestFlagPar = new ParameterBuilder();
        requestFlagPar.name("request-flag").description("请求标记").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        list.add(requestFlagPar.build());
        list.add(tokenPar.build());

        return SwaggerContext.createDocket(groupName, packageName, apiInfo(), list, enable, host, projectName);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                //大标题
                .title(title)
                //详细描述
                .description(description)
                //版本
                .version(version)
                .termsOfServiceUrl(termsOfServiceUrl)
                //作者
                .contact(new Contact(contactName, contactUrl, contactEmail))
                .license("The Apache License, Version 2.0")
                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
                .build();
    }

}
