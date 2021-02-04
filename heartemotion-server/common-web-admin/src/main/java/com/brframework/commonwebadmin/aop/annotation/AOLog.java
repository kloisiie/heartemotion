package com.brframework.commonwebadmin.aop.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 加入管理员操作审计
 * @see com.brframework.commonwebadmin.aop.AdminOptionAspect
 * @author xu
 * @date 2019/10/9 14:10
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AOLog {
}
