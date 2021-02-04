package com.brframework.commonsecurity.core;

import com.alibaba.fastjson.JSON;
import com.brframework.commonsecurity.utils.JwtUtils;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Date;

/**
 * @Author xu
 * @Date 2018/1/31 0031 下午 7:32
 * 用户安全服务
 */
@Data
public class SecurityUserDetailsService implements UserDetailsService {
    /** 该JWT的签发者 */
    private String iss;
    /** 有效期 （秒） */
    private long exp;
    /** 密钥 */
    private String secret;

    private SecurityUserDetailsConverter converter;

    public SecurityUserDetailsService(String iss, long exp, String secret,
                                      SecurityUserDetailsConverter converter){
        this.iss = iss;
        this.exp = exp;
        this.secret = secret;
        this.converter = converter;
    }

    @Override
    public UserDetails loadUserByUsername(String token) throws UsernameNotFoundException {
        SecuritySubject parse = parse(token);

//        return new SecurityUserDetails(parse.getId(), parse.getUsername(), parse.getPassword(),
//                parse.getAuthorities(), false, Maps.newHashMap());
        return converter.converter(parse);
    }

    public SecuritySubject parse(String token){
        return JSON.parseObject(JwtUtils.parse(token, secret).getSubject(), SecuritySubject.class);
    }

    /**
     * 生成token
     * @param aud token对应的用户标识
     * @param sub token存放的数据
     * @return
     */
    public String genToken(String aud, SecuritySubject sub){
        return JwtUtils.create(iss, JSON.toJSONString(sub), aud, new Date(), secret, SignatureAlgorithm.HS256);
    }
}
