package com.brframework.apidoc.web;

import com.brframework.apidoc.entity.MockTemplate;
import com.brframework.apidoc.json.MockListNodeResult;
import com.brframework.apidoc.json.MockParam;
import com.brframework.apidoc.json.MockSaveParam;
import com.brframework.apidoc.json.MockStatus;
import com.brframework.apidoc.service.ApiDocService;
import com.brframework.commonweb.json.JSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 接口文档接口
 * @author xu
 * @date 2020/4/20 14:57
 */
@RestController
@Api(tags = "接口文档")
@RequestMapping("/doc")
@Slf4j
public class ApiDocController {

    @Autowired
    ApiDocService apiDocService;

    @ApiOperation(value = "保存MOCK模板", notes = "保存MOCK模板")
    @PostMapping("v1/mock/template/save")
    public JSONResult saveMockTemplate(@RequestBody MockSaveParam param) {
        apiDocService.saveMockTemplate(param.getApiUrl(), param.getHttpMethod().toUpperCase(), param.getTemplate());
        return JSONResult.ok();
    }

    @ApiOperation(value = "正在打开MOCK的接口信息", notes = "正在打开MOCK的接口信息")
    @PostMapping("v1/mock/open/list")
    public JSONResult<List<MockListNodeResult>> openList() {
        return JSONResult.ok(apiDocService.listAllByOpen().stream()
                .map(temp -> {
                    MockListNodeResult result = new MockListNodeResult();
                    result.setApiUrl(temp.getApiUrl());
                    result.setHttpMethod(temp.getHttpMethod());
                    result.setTemplate(temp.getTemplate());
                    return result;
                }).collect(Collectors.toList()));
    }

    @ApiOperation(value = "重置MOCK模板", notes = "保存MOCK模板")
    @PostMapping("v1/mock/template/reset")
    public JSONResult<String> resetMockTemplate(@RequestBody MockParam param) {
        apiDocService.removeMockTemplate(param.getApiUrl(), param.getHttpMethod().toUpperCase());
        return getMockTemplate(param);
    }


    @ApiOperation(value = "获取MOCK模板", notes = "获取MOCK模板")
    @GetMapping("v1/mock/template")
    public JSONResult<String> getMockTemplate(MockParam param) {
        String template;
        MockTemplate mockTemplate = apiDocService.findMockTemplate(param.getApiUrl(), param.getHttpMethod().toUpperCase());
        if(mockTemplate != null){
            template = mockTemplate.getTemplate();
        } else {
            template = apiDocService.findDefaultMockTemplate(param.getApiUrl(), param.getHttpMethod());
        }

        return JSONResult.ok(template);
    }

    @ApiOperation(value = "获取接口MOCK的开启关闭状态", notes = "开启接口MOCK功能")
    @GetMapping("v1/mock/status")
    public JSONResult<MockStatus> getMockStatus(MockParam param) {
        MockStatus status = new MockStatus();
        status.setOpen(apiDocService.isOpenMock(param.getApiUrl(), param.getHttpMethod()));
        return JSONResult.ok(status);
    }

    @ApiOperation(value = "开启接口MOCK功能", notes = "开启接口MOCK功能")
    @PostMapping("v1/mock/open")
    public JSONResult mockOpen(@RequestBody MockParam param) {
        apiDocService.openMock(param.getApiUrl(), param.getHttpMethod());
        return JSONResult.ok();
    }


    @ApiOperation(value = "关闭接口MOCK功能", notes = "关闭接口MOCK功能")
    @PostMapping("v1/mock/closed")
    public JSONResult mockClosed(@RequestBody MockParam param) {
        apiDocService.closedMock(param.getApiUrl(), param.getHttpMethod());
        return JSONResult.ok();
    }

}
