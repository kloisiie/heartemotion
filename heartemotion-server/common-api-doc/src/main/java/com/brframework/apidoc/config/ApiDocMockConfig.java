package com.brframework.apidoc.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author xu
 * @date 2020/4/22 23:48
 */
@Data
@Component
@ConfigurationProperties(value = "api-doc.mock")
public class ApiDocMockConfig {

    /** 是否开启mock */
    private boolean enable;
    /** 默认打开mock的url配置 */
    private String defaultOpen;

}
