package com.hyuga.xinlv.bean;

/**
 * Created by gdjie on 2017/1/11.
 */

public class HttpResult<T> {
    private int code;
    private String msg;

    private T data;

    public T getData() {
        return data;
    }


    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "HttpResult{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
