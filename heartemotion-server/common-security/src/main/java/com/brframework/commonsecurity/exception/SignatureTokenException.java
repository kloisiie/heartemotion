package com.brframework.commonsecurity.exception;

import com.brframework.commonweb.exception.HandleException;

/**
 * token 签名异常
 * @Author xu
 * @Date 2018/2/2 0002 下午 3:00
 */
public class SignatureTokenException extends SecurityException {

    public SignatureTokenException(){
        super(403, "签名异常", "error_token_signature");
    }


}
