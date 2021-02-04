package com.brframework.commonwebbase.domain.impl;


import com.brframework.commonwebbase.domain.AbstractConfigService;

/**
 * 配置
 * 最大化适应需求变更
 * @author xu
 * @date 2019/10/28 17:49
 */
public class ConfigServiceImpl {

    public static  <T> AbstractConfigService<T> createConfigService(String key, Class<T> domainClass){
        return new AbstractConfigService<T>(){
            @Override
            public String getKey() {
                return key;
            }
            @Override
            public Class<T> getDomainClass() {
                return domainClass;
            }
        };
    }


}
