package com.brframework.commonsecurity.exception;

import com.brframework.commonweb.exception.HandleException;

/**
 * token 授权过期
 * @Author xu
 * @Date 2018/2/2 0002 下午 3:00
 */
public class ExpiredTokenException extends SecurityException {

    public ExpiredTokenException(){
        super(403, "授权过期", "error_token_expired");
    }


}
