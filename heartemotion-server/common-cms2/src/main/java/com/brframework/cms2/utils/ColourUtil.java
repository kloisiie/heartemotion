package com.brframework.cms2.utils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 请求方式配色工具类
 *
 * @author 揭光智
 * @date 2019/02/15
 */
public class ColourUtil {

    private static final Map<String, EnumColourElement> COLOURS;

    static {
        Map<String, EnumColourElement> requestMethodElements = new HashMap<>();
        requestMethodElements.put("GET", EnumColourElement.GET);
        requestMethodElements.put("POST", EnumColourElement.POST);
        requestMethodElements.put("PUT", EnumColourElement.PUT);
        requestMethodElements.put("DELETE", EnumColourElement.DELETE);
        requestMethodElements.put("HEAD", EnumColourElement.HEAD);
        requestMethodElements.put("PATCH", EnumColourElement.PATCH);
        requestMethodElements.put("OPTIONS", EnumColourElement.OPTIONS);
        requestMethodElements.put("TRACE", EnumColourElement.TRACE);
        COLOURS = Collections.unmodifiableMap(requestMethodElements);
    }

    /**
     * 根据请求的方式设置按钮的颜色
     *
     * @param requestMethod 请求方式
     */
    public static EnumColourElement setColorByRequestMethod(String requestMethod) {
        //新版本后， 颜色功能已经取消
        return EnumColourElement.GET;
    }
}
