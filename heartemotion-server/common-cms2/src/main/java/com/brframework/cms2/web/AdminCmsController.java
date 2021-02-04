package com.brframework.cms2.web;

import com.brframework.cms2.core.CmsDict;
import com.brframework.cms2.json.admin.cms.CmsDictEntry;
import com.brframework.cms2.service.cms.CmsDictService;
import com.brframework.commonweb.annotation.SwaggerIgnore;
import com.brframework.commonweb.json.JSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 核心模块
 * @author xu
 * @date 2020/11/12 16:47
 */
@RestController
@Api(tags = "核心模块")
@RequestMapping("/admin/access")
@Slf4j
public class AdminCmsController {

    @Autowired
    CmsDictService cmsDictService;

    @GetMapping("/v1/cms/dict/{key}")
    @SwaggerIgnore
    public JSONResult<List<CmsDictEntry>> dict(@PathVariable String key){
        CmsDict cmsDict = cmsDictService.getCmsDict(key);

        return JSONResult.ok(cmsDict.dictEntry());
    }


    @GetMapping("/v1/cms/dict/{key}/{childKey1}")
    @SwaggerIgnore
    public JSONResult<List<CmsDictEntry>> dictChild1(@PathVariable String key,
                                                     @PathVariable(required = false) String childKey1){
        CmsDict cmsDict = cmsDictService.getCmsDict(key);

        return JSONResult.ok(cmsDict.dictEntry(childKey1));
    }


    @GetMapping("/v1/cms/dict/{key}/{childKey1}/{childKey2}")
    @SwaggerIgnore
    public JSONResult<List<CmsDictEntry>> dictChild2(@PathVariable String key, @PathVariable(required = false) String childKey1,
                                                     @PathVariable(required = false) String childKey2){
        CmsDict cmsDict = cmsDictService.getCmsDict(key);

        return JSONResult.ok(cmsDict.dictEntry(childKey1, childKey2));
    }

    @ApiOperation(value = "字典查询", notes = "字典查询")
    @GetMapping("/v1/cms/dict/{key}/{childKey1}/{childKey2}/{childKey3}")
    public JSONResult<List<CmsDictEntry>> dictChild3(@PathVariable String key, @PathVariable(required = false) String childKey1,
                                                     @PathVariable(required = false) String childKey2, @PathVariable(required = false) String childKey3){
        CmsDict cmsDict = cmsDictService.getCmsDict(key);

        return JSONResult.ok(cmsDict.dictEntry(childKey1, childKey2, childKey3));
    }

}
