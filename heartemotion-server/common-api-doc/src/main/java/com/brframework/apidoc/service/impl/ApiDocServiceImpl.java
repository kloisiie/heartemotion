package com.brframework.apidoc.service.impl;

import com.alibaba.fastjson.JSON;
import com.brframework.apidoc.config.ApiDocMockConfig;
import com.brframework.apidoc.dao.MockTemplateDao;
import com.brframework.apidoc.entity.MockTemplate;
import com.brframework.apidoc.service.ApiDocService;
import com.brframework.apidoc.utils.MockjsUtil;
import com.brframework.apidoc.utils.ApiMockUtils;
import com.brframework.apidoc.utils.PatternMatcher;
import com.brframework.apidoc.utils.ServletPathMatcher;
import com.brframework.commonweb.core.SwaggerContext;
import com.brframework.commonweb.json.JSONResult;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ResolvableType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 接口文档服务
 *
 * @author xu
 * @date 2020/4/20 14:10
 */
@Service
@Slf4j
public class ApiDocServiceImpl implements ApiDocService {

    private final MockTemplateDao mockTemplateDao;
    private final RequestMappingHandlerMapping requestMappingHandlerMapping;
    private final ApiDocMockConfig apiDocMockConfig;
    private final PatternMatcher pathMatcher = new ServletPathMatcher();
    private final Set<String> defaultOpenPattern;
    //当前项目的项目名
    @Value("${swagger.project.name}")
    private String projectName;

    @Autowired
    public ApiDocServiceImpl(MockTemplateDao mockTemplateDao,
                             RequestMappingHandlerMapping requestMappingHandlerMapping, ApiDocMockConfig apiDocMockConfig) {
        this.mockTemplateDao = mockTemplateDao;
        this.requestMappingHandlerMapping = requestMappingHandlerMapping;
        this.apiDocMockConfig = apiDocMockConfig;
        if(StringUtils.isNotEmpty(apiDocMockConfig.getDefaultOpen())){
            defaultOpenPattern = Sets.newHashSet(apiDocMockConfig.getDefaultOpen().split("\\s*,\\s*"));
        } else {
            defaultOpenPattern = Sets.newHashSet();
        }
    }

    @Override
    public List<MockTemplate> listAllByOpen() {
        return mockTemplateDao.findByStatus(MockTemplate.STATUS_OPEN);
    }

    @Override
    public MockTemplate findMockTemplate(String apiUrl, String method) {
        return mockTemplateDao.findByApiUrlAndHttpMethod(getRawApiUrl(apiUrl, method), method);
    }

    @Override
    @Transactional
    public MockTemplate saveMockTemplate(String apiUrl, String method, String template) {
        MockTemplate mockTemplate = Optional.ofNullable(findMockTemplate(apiUrl, method)).orElse(
                MockTemplate.builder()
                        .createDate(LocalDateTime.now())
                        .apiUrl(getRawApiUrl(apiUrl, method))
                        .httpMethod(method)
                        .status(MockTemplate.STATUS_OPEN)
                        .build()
        );

        mockTemplate.setTemplate(template);
        return mockTemplateDao.save(mockTemplate);
    }

    @Override
    @Transactional
    public void removeMockTemplate(String apiUrl, String method) {
        MockTemplate mockTemplate = findMockTemplate(apiUrl, method);
        if (mockTemplate == null) {
            return;
        }
        mockTemplateDao.deleteById(mockTemplate.getId());
    }

    @Override
    public String mockApi(String apiUrl, String method) {
        MockTemplate mockTemplate = findMockTemplate(apiUrl, method);
        String template;
        if (mockTemplate == null) {
            template = findDefaultMockTemplate(apiUrl, method);
        } else {
            template = mockTemplate.getTemplate();
        }
        return MockjsUtil.mock(template);
    }

    @Override
    public String findDefaultMockTemplate(String apiUrl, String method) {

        HandlerMethod handlerMethod = getHandlerMethod(getRequestMappingInfo(apiHandle(apiUrl), method));
        if(handlerMethod == null){
            throw new RuntimeException("请关闭MOCK！当前请求不支持 URL:" + apiUrl);
        }

        ResolvableType methodType = ResolvableType.forMethodReturnType(handlerMethod.getMethod());
        try {
            return JSON.toJSONString(ApiMockUtils.mockType(methodType, null));
        } catch (Exception e) {
            log.error("数据mock错误", e);
            return null;
        }
    }

    private String getRawApiUrl(String apiUrl, String method){
        //因为存在/api/detail/{id} 这种url，所以前端传递的url就是/api/detail/1，需要传入原始的url后再做查询
        RequestMappingInfo requestMappingInfo = getRequestMappingInfo(apiHandle(apiUrl), method);
        if(requestMappingInfo == null){
            return null;
        }
        return requestMappingInfo.getPatternsCondition().getPatterns().toArray()[0].toString();
    }

    private String apiHandle(String apiUrl){
        if (!projectName.equals(SwaggerContext.DEFAULT_PROJECT_NAME)) {
            String prefix = "/" + projectName;
            if (apiUrl.startsWith(prefix)) {
                apiUrl = apiUrl.substring(prefix.length());
            }
        }
        return apiUrl;
    }

    private RequestMappingInfo getRequestMappingInfo(String apiUrl, String method){
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = requestMappingHandlerMapping
                .getHandlerMethods();
        List<RequestMappingInfo> collect = handlerMethods.keySet().stream()
                .filter(mappingInfo -> mappingInfo.getPatternsCondition().getMatchingPatterns(apiUrl).size() != 0)
                .filter(mappingInfo -> mappingInfo.getMethodsCondition().getMethods().contains(RequestMethod.valueOf(method.toUpperCase())))
                .collect(Collectors.toList());

        if(collect.size() == 0){
            return null;
        }

        return collect.get(0);
    }

    private HandlerMethod getHandlerMethod(RequestMappingInfo mappingInfo){
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = requestMappingHandlerMapping
                .getHandlerMethods();
        return handlerMethods.get(mappingInfo);
    }

    @Override
    @Transactional
    public void openMock(String apiUrl, String method) {
        MockTemplate mockTemplate = findMockTemplate(apiUrl, method);
        if(mockTemplate == null){
            mockTemplate = saveMockTemplate(apiUrl, method, findDefaultMockTemplate(apiUrl, method));
        }
        mockTemplate.setStatus(MockTemplate.STATUS_OPEN);
        mockTemplateDao.save(mockTemplate);
    }

    @Override
    @Transactional
    public void closedMock(String apiUrl, String method) {
        MockTemplate mockTemplate = findMockTemplate(apiUrl, method);
        if(mockTemplate == null){
            mockTemplate = saveMockTemplate(apiUrl, method, findDefaultMockTemplate(apiUrl, method));
        }
        mockTemplate.setStatus(MockTemplate.STATUS_CLOSED);
        mockTemplateDao.save(mockTemplate);
    }

    @Override
    public boolean isOpenMock(String apiUrl, String method) {
        MockTemplate mockTemplate = findMockTemplate(apiUrl, method);
        if(mockTemplate == null){
            if(StringUtils.isEmpty(apiDocMockConfig.getDefaultOpen())){
                return false;
            }

            return defaultOpenPattern.stream().anyMatch(pattern -> pathMatcher.matches(pattern, apiUrl));
        }
        int status = mockTemplate.getStatus() == null ? MockTemplate.STATUS_CLOSED : mockTemplate.getStatus();

        return status == MockTemplate.STATUS_OPEN;
    }
}
