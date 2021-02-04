package com.brframework.commondb.annotation;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 数据排序
 * @see PageRequest
 * @author xu
 * @date 2019/1/9 11:51
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface QuerySort {

    /**
     * 数据排序策略
     * @return
     */
    Sort.Direction direction() default Sort.Direction.ASC;

    /**
     * 排序的字段
     * @return
     */
    String[] properties();

}
