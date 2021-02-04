package com.brframework.commonsecurity.utils;

import com.brframework.commonsecurity.exception.ExpiredTokenException;
import com.brframework.commonsecurity.exception.SignatureTokenException;
import com.brframework.commonsecurity.exception.UnauthorizedTokenException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.DefaultClaims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * Jwt创建工具
 * @Author xu
 * @Date 2018/1/25 0025 下午 1:34
 */
public class JwtUtils {

    /** logger */
    private static Logger log = LoggerFactory.getLogger(JwtUtils.class);

    /**
     * 生成JWT
     * @param iss 该JWT的签发者
     * @param sub 该JWT所面向的用户
     * @param aud 接收该JWT的一方
     * @param iat 创建时间
     * @return
     */
    public static String create(String iss, String sub, String aud, Date iat, String secret, SignatureAlgorithm signature){



        Claims claims = new DefaultClaims();
        claims.setIssuer(iss);
        claims.setSubject(sub);
        claims.setAudience(aud);
        claims.setIssuedAt(iat);


        String compactJws = Jwts.builder()
                .setClaims(claims)
                .signWith(signature, secret)
                .compact();

        return compactJws;
    }

    /**
     * 解析JWT
     * @param compactJws
     * @return
     */
    public static Claims parse(String compactJws, String secret){

        try{
            Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(compactJws).getBody();

            return claims;
        } catch (SignatureException e){
            log.error("签名验证失败，遭到了篡改，TOKEN：{}", compactJws);
            throw new SignatureTokenException();
        } catch (ExpiredJwtException e){
            log.error("签名已经过期，TOKEN：{}", compactJws);
            throw new ExpiredTokenException();
        } catch (Exception e){
            log.error("JWT解析失败，TOKEN：{}，TOKEN：{}", compactJws);
            throw new UnauthorizedTokenException();
        }




    }

}
