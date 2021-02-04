package com.brframework.commonweb.core;

import com.alibaba.fastjson.JSON;
import com.fasterxml.classmate.ResolvedType;
import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import springfox.documentation.schema.Annotations;
import springfox.documentation.service.AllowableListValues;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.schema.ModelPropertyBuilderPlugin;
import springfox.documentation.spi.schema.contexts.ModelPropertyContext;
import springfox.documentation.swagger.schema.ApiModelProperties;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 针对枚举做的特殊处理
 * @author xu
 * @date 2020/4/24 19:16
 */
@Component
public class EnumPropertyPlugin implements ModelPropertyBuilderPlugin {

    @Override
    public void apply(ModelPropertyContext context) {
        Optional<ApiModelProperty> annotation = Optional.absent();

        if (context.getAnnotatedElement().isPresent()) {
            annotation = annotation.or(ApiModelProperties.findApiModePropertyAnnotation(context.getAnnotatedElement().get()));
        }
        if (context.getBeanPropertyDefinition().isPresent()) {
            annotation = annotation.or(Annotations.findPropertyAnnotation(
                    context.getBeanPropertyDefinition().get(),
                    ApiModelProperty.class));
        }
        final Class<?> rawPrimaryType = context.getBeanPropertyDefinition().get().getRawPrimaryType();
        //过滤得到目标类型
        if (annotation.isPresent() && rawPrimaryType.isEnum()) {

            List<String> allowableValues = Lists.newArrayList(rawPrimaryType.getEnumConstants()).stream().map(object -> {
                Class<?> cla = object.getClass();
                Method[] methods = cla.getDeclaredMethods();
                List<String> value = Stream.of(methods)
                        .filter(method -> !method.getName().equals("valueOf") && !method.getName().equals("values"))
                        .filter(method -> method.getParameterCount() == 0)
                        .map(method -> {
                            try {
                                return method.invoke(object).toString();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            return null;
                        }).collect(Collectors.toList());
                return object.toString() + "(" + StringUtils.join(value, ",") + ")";
            }).collect(Collectors.toList());

            context.getBuilder().allowableValues(new AllowableListValues(allowableValues, rawPrimaryType.getTypeName()));

        }
    }

    @Override
    public boolean supports(DocumentationType documentationType) {
        return true;
    }

}
