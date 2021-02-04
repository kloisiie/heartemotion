package com.brframework.apidoc.utils;

import cn.hutool.core.util.EnumUtil;
import cn.hutool.core.util.ReflectUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.brframework.common.utils.DateTimeUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.swagger.annotations.ApiModelProperty;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.ResolvableType;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author xu
 * @date 2020/4/15 17:38
 */
@Slf4j
public class ApiMockUtils {


    /**
     * MOCK某一个类的数据
     * @param classType  MOCK类
     * @return           MOCK数据
     */
    public static Object mockClass(ResolvableType classType) throws Exception {
        Class<?> returnType = Objects.requireNonNull(classType.toClass());
        Object result = returnType.getConstructor().newInstance();

        Lists.newArrayList(returnType.getDeclaredFields()).stream()
                .filter(field -> {
                    ApiModelProperty annotation = field.getAnnotation(ApiModelProperty.class);
                    return annotation != null;
                })
                .forEach(field -> {
                    ApiModelProperty annotation = field.getAnnotation(ApiModelProperty.class);
                    assert annotation != null;
                    field.setAccessible(true);
                    try {
                        //如果字段的类型为<T>，Field.getType得到的将是T
                        //通过#ResolvableType.forField可理解<T>所代表类型
                        field.set(result, mockType(ResolvableType.forField(field, classType),
                                annotation.example()));

                    } catch (Exception e) {
                        log.error("API MOCK 错误", e);
                    }
                });

        return result;
    }

    /**
     * MOCK某一个数据类型的数据
     * @param type       MOCK类型
     * @param example    MOCK数据
     * @return           该数据类型的MOCK数据
     */
    public static Object mockType(ResolvableType type, String example) throws Exception {
        String typeName = type.toClass().getName();
        if(typeName.equals(String.class.getName())){
            return example;
        } else if(typeName.equals(Integer.class.getName()) ||
                typeName.equals("int")){
            return Integer.parseInt(example);
        } else if(typeName.equals(Double.class.getName()) ||
                typeName.equals("double")){
            return Double.parseDouble(example);
        } else if(typeName.equals(Float.class.getName()) ||
                typeName.equals("float")){
            return Float.parseFloat(example);
        } else if(typeName.equals(Long.class.getName()) ||
                typeName.equals("long")){
            return Long.parseLong(example);
        } else if(typeName.equals(Short.class.getName()) ||
                typeName.equals("short")){
            return Short.parseShort(example);
        } else if(typeName.equals(Boolean.class.getName()) ||
                typeName.equals("boolean")){
            return Boolean.parseBoolean(example);
        } else if(typeName.equals(LocalDateTime.class.getName())){
            return DateTimeUtils.stringToDateTime(example);
        } else if(typeName.equals(BigDecimal.class.getName())){
            return new BigDecimal(example);
        } else if(type.isArray()){
            //数组类型，只考虑一维数组
            if(StringUtils.isNotEmpty(example)){
                return JSONArray.parseArray(example);
            } else {
                ResolvableType newFieldType = type.getComponentType();
                Object[] arrayMock = (Object[]) Array.newInstance(newFieldType.getRawClass() , 1);
                arrayMock[0] = mockType(newFieldType, null);
                return arrayMock;
            }

        } else if(List.class.isAssignableFrom(type.toClass())){
            if(StringUtils.isNotEmpty(example)){
                return JSONArray.parseArray(example);
            } else {
                ResolvableType newFieldType = type.getGeneric(0);
                return Lists.newArrayList(mockType(newFieldType, null));
            }

        } else if(type.toClass().isEnum()){
            return ReflectUtil.getMethodByName(type.toClass(), "valueOf").invoke(null, example);
        } else if(Map.class.isAssignableFrom(type.toClass())){
            if(StringUtils.isNotEmpty(example)){
                return JSONObject.parseObject(example).getInnerMap();
            } else {
                ResolvableType newFieldType = type.getGeneric(1);
                HashMap<String, Object> map = Maps.newHashMap();
                map.put("key", mockType(newFieldType, null));
                return map;
            }
        } else {
            return mockClass(type);
        }
    }

}
