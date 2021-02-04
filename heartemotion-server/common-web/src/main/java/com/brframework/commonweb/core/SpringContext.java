package com.brframework.commonweb.core;

import com.google.common.collect.Lists;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author xu
 * @date 2018/10/31 10:14
 */
@Component
public class SpringContext implements ApplicationContextAware {
    private static ApplicationContext applicationContext;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContext.applicationContext = applicationContext;
    }
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }
    public static <T> T getBean(String name) {
        return (T) getApplicationContext().getBean(name);
    }
    public static <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }
    public static <T> T getBean(String name, Class<T> clazz) {
        return getApplicationContext().getBean(name, clazz);
    }

    /**
     * 获取同一类型的多个bean
     * @param type
     * @param <T>
     * @return
     */
    public static <T> List<T> getBeansOfType(Class<T> type){
        return Lists.newArrayList(getApplicationContext().getBeansOfType(type).values());
    }

    public static <T> Map<String, T> getBeansOfTypeToMap(Class<T> type){
        return getApplicationContext().getBeansOfType(type);
    }
}
