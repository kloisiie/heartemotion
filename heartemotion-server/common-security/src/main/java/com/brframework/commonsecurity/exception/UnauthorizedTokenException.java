package com.brframework.commonsecurity.exception;

import com.brframework.commonweb.exception.HandleException;

/**
 * token 不合法
 * @Author xu
 * @Date 2018/2/2 0002 下午 3:00
 */
public class UnauthorizedTokenException extends SecurityException {

    public UnauthorizedTokenException(){
        super(403, "未授权", "error_token_unauthorized");
    }


}
