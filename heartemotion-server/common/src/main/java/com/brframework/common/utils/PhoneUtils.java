package com.brframework.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by xu
 *
 * 手机号码工具
 *
 * @author xu
 * @date 18-6-6 下午1:21
 */
public class PhoneUtils {

    /**
     * 判断是否是一个中国的手机号码
     * @param phone
     * @return
     */
    public static boolean isPhoen(String phone){
        String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
        if (phone.length() != 11) {
            return false;
        } else {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(phone);
            return m.matches();
        }
    }

}
