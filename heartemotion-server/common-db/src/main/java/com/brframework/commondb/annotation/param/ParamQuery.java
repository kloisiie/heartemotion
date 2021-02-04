package com.brframework.commondb.annotation.param;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 参数查询
 * @author xu
 * @date 2019/1/9 11:07
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ParamQuery {

    /**
     * 查询语法
     * @return
     */
    QueryExpression expression() default QueryExpression.eq;

    /**
     * 用户将两个字段关联到一起比如between，并且是实体对应的字段名
     * @return
     */
    String betweenKey() default "";

}
