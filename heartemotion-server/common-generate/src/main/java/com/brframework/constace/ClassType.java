package com.brframework.constace;

/**
 * @author xu
 * @date 2020/7/30 14:28
 */
public enum ClassType {

    CLASS("class"), INTERFACE("interface");

    String code;

    public String code(){
        return code;
    }

    ClassType(String code){
        this.code = code;
    }

}
