package com.brframework.commonsecurity.exception;

import com.brframework.commonweb.exception.HandleException;
import com.brframework.commonweb.json.JSONResult;

/**
 * 安全
 * @author xu
 * @date 2019/8/24 14:02
 */
public class SecurityException extends HandleException {

    public static int DEFAULT_CODE = 403;
    public static String DEFAULT_MSG = "授权错误";
    public static String DEFAULT_DESC = "error_security";

    public SecurityException(){
        setCode(DEFAULT_CODE);
        setMsg(DEFAULT_MSG);
        setDesc(DEFAULT_DESC);
    }

    public SecurityException(String msg){
        setCode(DEFAULT_CODE);
        setMsg(msg);
        setDesc(DEFAULT_DESC);
    }

    public SecurityException(int code, String msg, String desc){
        setCode(code);
        setMsg(msg);
        setDesc(desc);
    }

}
