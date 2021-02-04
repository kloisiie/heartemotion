package com.brframework.utils;

import cn.hutool.core.io.IoUtil;
import com.google.common.collect.Maps;
import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * @author xu
 * @date 2020/7/29 15:15
 */
public class TemplateUtils {

    public static String readResource(String path){
        return IoUtil.read(TemplateUtils.class.getResourceAsStream(path),
                "UTF-8");
    }

    @SneakyThrows
    public static Map<String, String> objectToMap(Object object){
        Map<String, String> map = Maps.newHashMap();
        Field[] declaredFields = object.getClass().getDeclaredFields();
        for (Field declaredField : declaredFields) {
            declaredField.setAccessible(true);
            map.put(declaredField.getName(), declaredField.get(object).toString());
        }
        return map;
    }

    public static String setTemplateAttribute(String templateContent, Map<String, String> attributes){
        for (String key : attributes.keySet()) {
            templateContent = templateContent.replaceAll("\\$\\{" + key + "\\}", attributes.get(key));
        }
        return templateContent;
    }

}
