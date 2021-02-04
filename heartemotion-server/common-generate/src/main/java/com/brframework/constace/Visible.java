package com.brframework.constace;

/**
 * @author xu
 * @date 2020/7/29 16:32
 */
public enum  Visible {
    PUBLIC("public"), PRIVATE("private"), DEFAULT(""), PROTECTED("protected");

    String code;
    public String code(){
        return code;
    }
    Visible(String code){
        this.code = code;
    }

}
