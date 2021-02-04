package com.brframework.cms2.utils;

/**
 * @author 揭光智
 * @date 2019/02/17
 */
public enum EnumColourElement {


    /**
     * 颜色表
     */
    NONE(null),
    //Http请求对应胡颜色表,配色参考Swagger文档.
    GET("#0F6AB4"),
    POST("#10A54A"),
    PUT("#C5862B"),
    DELETE("#A41E22"),
    HEAD("#9012FE"),
    PATCH("#50E3C2"),
    OPTIONS("#0D5AA7"),
    TRACE("#FAFAFA"),
    //常用颜色表
    RED("#FF4E56"),
    GREEN("#00FF00"),
    BLUE("#0000FF"),
    WHITE("#FFFFFF"),
    BLACK("#000000");
    //自定义颜色表

    private final String color;

    EnumColourElement(String color) {
        this.color = color;
    }

    public String color() {
        return color;
    }
}
