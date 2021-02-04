package cn.bluetech.gragas.utils;

import cn.hutool.core.util.ReUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 密码工具
 * @author xu
 * @date 2020/2/21 10:07
 */
public class PasswordUtils {

    private static final String PASSWORD_REG_D = ".+\\d+.+";
    private static final String PASSWORD_REG_LETTER = ".+[A-Za-z]+.+";
    private static final String PASSWORD_REG_SYBOL = ".+[^A-Za-z0-9]+.+";

    /**
     * 验证密码是否正确
     * @param rawPassword        需要验证的未加密的密码
     * @param encodedPassword    加密了的正确密码
     * @return 是否匹配
     */
    public static boolean isMatches(CharSequence rawPassword, String encodedPassword){
        return new BCryptPasswordEncoder().matches(rawPassword, encodedPassword);
    }

    /**
     * 密码加密
     * @param password
     * @return
     */
    public static String encodePassword(String password){
        return new BCryptPasswordEncoder().encode(password);
    }


    /**
     * 是否是一个合格的密码
     * @param password
     * @return
     */
    public static boolean isStandardPassword(String password){
        if(password.length() < 8 || password.length() > 20){
            return false;
        }

        int count = 0;

        if(ReUtil.isMatch(PASSWORD_REG_D, password)){
            count ++;
        }

        if(ReUtil.isMatch(PASSWORD_REG_LETTER, password)){
            count ++;
        }

        if(ReUtil.isMatch(PASSWORD_REG_SYBOL, password)){
            count ++;
        }

        return count >= 2;
    }



}
