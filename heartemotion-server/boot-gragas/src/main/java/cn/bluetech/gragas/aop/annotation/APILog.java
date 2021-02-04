package cn.bluetech.gragas.aop.annotation;

import cn.bluetech.gragas.aop.ApiLogAspect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 加入API请求日志
 * @see ApiLogAspect
 * @author xu
 * @date 2019/10/9 14:10
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface APILog {
}
