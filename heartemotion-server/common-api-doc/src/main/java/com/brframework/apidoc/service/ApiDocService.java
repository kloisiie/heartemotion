package com.brframework.apidoc.service;

import com.brframework.apidoc.entity.MockTemplate;
import org.springframework.http.HttpMethod;

import java.util.List;

/**
 * @author xu
 * @date 2020/4/20 14:09
 */
public interface ApiDocService {

    /**
     * 查询所有打开的MOCK模板
     * @return  所有打开的MOCK模板
     */
    List<MockTemplate> listAllByOpen();

    /**
     * 查询MOCK模板
     * @param apiUrl     URL
     * @param method     http method
     * @return  MockTemplate
     */
    MockTemplate findMockTemplate(String apiUrl, String method);

    /**
     * 保存MOCK模板
     * @param apiUrl      URL
     * @param method      http method
     * @param template    mockjs 模板
     */
    MockTemplate saveMockTemplate(String apiUrl, String method, String template);

    /**
     * 删除MOCK模板
     * @param apiUrl      URL
     * @param method      http method
     */
    void removeMockTemplate(String apiUrl, String method);

    /**
     * MOCK API 数据
     * @param apiUrl      URL
     * @param method      http method
     * @return JSON
     */
    String mockApi(String apiUrl, String method);

    /**
     * 获取默认MOCK 模板
     * @param apiUrl      URL
     * @param method      http method
     * @return 默认MOCK模板
     */
    String findDefaultMockTemplate(String apiUrl, String method);

    /**
     * 开启mock功能
     * @param apiUrl      URL
     * @param method      http method
     */
    void openMock(String apiUrl, String method);

    /**
     * 关闭mock功能
     * @param apiUrl      URL
     * @param method      http method
     */
    void closedMock(String apiUrl, String method);

    /**
     * 是否开启mock功能
     * @param apiUrl      URL
     * @param method      http method
     */
    boolean isOpenMock(String apiUrl, String method);

}
