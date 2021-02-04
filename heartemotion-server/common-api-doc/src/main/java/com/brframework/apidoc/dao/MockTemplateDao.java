package com.brframework.apidoc.dao;

import com.brframework.apidoc.entity.MockTemplate;
import com.brframework.commondb.core.CommonRepository;

import java.util.List;

/**
 * @author xu
 * @date 2020/4/20 14:08
 */
public interface MockTemplateDao extends CommonRepository<Long, MockTemplate> {

    /**
     * 通过URL和HTTP METHOD进行模板查询
     * @param apiUrl       URL
     * @param httpMethod   http method
     * @return  MOCK 模板
     */
    MockTemplate findByApiUrlAndHttpMethod(String apiUrl, String httpMethod);

    /**
     * 通过状态查询
     * @param status  状态
     * @return  MOCK 模板
     */
    List<MockTemplate> findByStatus(Integer status);
}
