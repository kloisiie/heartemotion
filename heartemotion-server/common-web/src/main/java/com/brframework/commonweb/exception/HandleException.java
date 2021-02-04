package com.brframework.commonweb.exception;

import com.brframework.commonweb.json.JSONResult;
import lombok.Data;

/**
 * 操作异常
 * @author xu
 * @date 2019/8/19 12:29
 */
@Data
public class HandleException extends RuntimeException {

    private int code = JSONResult.DEFAULT_ERROR_CODE;
    private String msg = JSONResult.DEFAULT_ERROR_MSG;
    private String desc = null;

    public HandleException(){
    }

    public HandleException(String msg){
        this.msg = msg;
    }

    public HandleException(int code, String msg, String desc){
        this.code = code;
        this.msg = msg;
        this.desc = desc;
    }

    @Override
    public String getMessage() {
        return msg;
    }
}
