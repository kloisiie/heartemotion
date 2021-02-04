package com.brframework.commonwebbase.domain;

import com.alibaba.fastjson.JSON;

import com.brframework.commonweb.exception.HandleException;
import com.brframework.commonwebbase.service.DictionaryService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * @author xu
 * @date 2019/10/28 17:29
 */
@Slf4j
public abstract class AbstractConfigService<Domain> implements ConfigService<Domain> {

    @Autowired
    DictionaryService dictionaryService;

    /**
     * 获取Key
     * @return
     */
    public abstract String getKey();

    /**
     * 获取Key对应的Class
     * @return
     */
    public abstract Class<Domain> getDomainClass();

    @Override
    public Domain get() {
        String s = dictionaryService.get(getKey());
        if(StringUtils.isEmpty(s)){
            try {
                return getDomainClass().newInstance();
            } catch (Throwable e) {
                log.error("对象实例化错误，请查看是否有无参构造方法", e);
                throw new HandleException("对象实例化错误，请查看是否有无参构造方法");
            }
        }
        return JSON.parseObject(s, getDomainClass());
    }

    @Override
    public void set(Domain config) {
        dictionaryService.set(getKey(), JSON.toJSONString(config));
    }

}
