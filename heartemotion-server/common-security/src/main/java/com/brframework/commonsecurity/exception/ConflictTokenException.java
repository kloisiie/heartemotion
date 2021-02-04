package com.brframework.commonsecurity.exception;

import com.brframework.commonweb.exception.HandleException;

/**
 * token冲突异常
 * 用户在其他地方登录
 * @author xu
 * @date 2019/8/19 19:38
 */
public class ConflictTokenException extends SecurityException {

    public ConflictTokenException(){
        super(403, "其他地方登录", "error_token_conflict");
    }

}
