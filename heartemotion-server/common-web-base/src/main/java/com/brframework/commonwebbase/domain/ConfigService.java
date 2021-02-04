package com.brframework.commonwebbase.domain;

/**
 * @author xu
 * @date 2019/10/28 17:27
 */
public interface ConfigService<Domain> {

    /**
     * 获取Domain
     * @return
     */
    Domain get();

    /**
     * 设置Domain
     * @param config
     */
    void set(Domain config);

}
