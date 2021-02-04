package cn.bluetech.gragas.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author xu
 */
@Data
@Component
@ConfigurationProperties(value = "security.rest")
public class SecurityUserDetailsConfig {

    /** 该JWT的签发者 */
    private String adminIss;
    /** 有效期 （秒） */
    private long adminExp;
    /** 签名 */
    private String adminSecret;
    /** 一个签发者中一个用户只能拥有一个JWT */
    private boolean adminSingleUser;

    /** api JWT的签发者 */
    private String apiIss;
    /** 有效期 （秒） */
    private long apiExp;
    /** 签名 */
    private String apiSecret;
    /** 一个签发者中一个用户只能拥有一个JWT */
    private boolean apiSingleUser;
}
