package com.brframework.commonweb.core;

import com.brframework.commonweb.annotation.SwaggerIgnore;
import org.apache.commons.lang3.StringUtils;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.ApiSelectorBuilder;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

/**
 * @Author xu
 * @Date 2018/1/3 0003 下午 3:30
 * Swagger api生成
 */
public class SwaggerContext {

    public static final String DEFAULT_PROJECT_NAME = "default";

    public static Docket createDocket(String groupName, String packageName, ApiInfo apiInfo, List<Parameter> pars, boolean enable, String host, String projectName){
        Docket docket = new Docket(DocumentationType.SWAGGER_2);
        String pathMapping = "/";
        if (StringUtils.isNotBlank(projectName) && !DEFAULT_PROJECT_NAME.equals(projectName)) {
            pathMapping = pathMapping + projectName;
        }
        docket.groupName(groupName)
                .host(host)
//                .genericModelSubstitutes(DeferredResult.class)
//                .useDefaultResponseMessages(false)
//                .forCodeGeneration(true)
                .globalOperationParameters(pars)
                // base，最终调用接口后会和paths拼接在一起
                .pathMapping(pathMapping);
        docket.enable(enable);
        ApiSelectorBuilder select = docket.apiInfo(apiInfo).select();

        select.apis(input -> {
            if (RequestHandlerSelectors.basePackage(packageName).apply(input)) {

                if (RequestHandlerSelectors.withClassAnnotation(SwaggerIgnore.class).apply(input) ||
                        RequestHandlerSelectors.withMethodAnnotation(SwaggerIgnore.class).apply(input)) {
                    return false;
                }
                return true;
            }
            return false;
        });


        select.paths(PathSelectors.any());

        return select.build();
    }

}
