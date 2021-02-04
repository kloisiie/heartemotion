package com.brframework.commonweb.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author xu
 * @Date 2018/1/5 0005 下午 1:41
 * 设置忽略不生成文档的Controller或者Controller里的方法
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface SwaggerIgnore {
}
