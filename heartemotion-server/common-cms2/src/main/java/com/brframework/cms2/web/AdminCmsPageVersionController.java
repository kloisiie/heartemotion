package com.brframework.cms2.web;

import java.lang.*;
import java.util.*;
import java.util.stream.Collectors;

import com.brframework.cms2.entity.cms.*;
import com.brframework.cms2.pojo.cms.*;
import com.brframework.cms2.service.cms.*;
import com.brframework.cms2.json.admin.cms.*;
import com.brframework.commonsecurity.core.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.extern.slf4j.Slf4j;
import com.brframework.commonweb.json.JSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import javax.validation.Valid;


/**
 * 页面版本模块
 * @author xu
 * @date 2020-11-07 18:37:22
 */ 
@RestController
@Api(tags = "页面版本模块")
@RequestMapping("/admin/access")
@Slf4j
public class AdminCmsPageVersionController{

    /** 页面版本Service */
    @Autowired
    public CmsPageVersionService cmsPageVersionService;

    
    @ApiOperation(value = "页面版本列表", notes = "页面版本列表")
    @GetMapping("/v1/cms-page-version/list/{pageId}")
    //@PreAuthorize("hasRole('cms-page-version-query')")
    public JSONResult<List<CmsPageVersionQueryResult>> cmsPageVersionListV1(@PathVariable Long pageId){
        return JSONResult.ok(cmsPageVersionService.listCmsPageVersionByPage(pageId).stream().parallel()
                .map(this::cmsPageVersionToResult)
                .collect(Collectors.toList())
        );
            
    }


    
    @ApiOperation(value = "页面版本详情", notes = "页面版本详情")
    @GetMapping("/v1/cms-page-version/details/{pageVersionId}")
    //@PreAuthorize("hasRole('cms-page-version-query')")
    public JSONResult<CmsPageVersionDetailResult> cmsPageVersionDetailsV1(@PathVariable Long pageVersionId){
        
        return JSONResult.ok(cmsPageVersionToDetailResult(cmsPageVersionService.findCmsPageVersionById(pageVersionId)));
            
    }

    
    @ApiOperation(value = "添加页面版本", notes = "添加页面版本")
    @PostMapping("/v1/cms-page-version/add")
    //@PreAuthorize("hasRole('cms-page-version-add')")
    public JSONResult cmsPageVersionAddV1(@Valid CmsPageVersionInsertParam insertParam){
        
        CmsPageVersionInsertParamDTO dtoParam = new CmsPageVersionInsertParamDTO();
        dtoParam.setName(insertParam.getName());
        dtoParam.setOwner(SecurityContextHolder.getUserDetails().getUsername());
        dtoParam.setPageId(insertParam.getPageId());
        dtoParam.setContent(insertParam.getContent());
    
        cmsPageVersionService.insertCmsPageVersion(dtoParam);
        return JSONResult.ok();
            
    }


    @ApiOperation(value = "删除页面版本", notes = "删除页面版本")
    @DeleteMapping("/v1/cms-page-version/del/{pageVersionId}")
    //@PreAuthorize("hasRole('cms-page-version-del')")
    public JSONResult cmsPageVersionDeleteV1(@PathVariable Long pageVersionId){
        
        cmsPageVersionService.removeCmsPageVersionById(pageVersionId);
        return JSONResult.ok();
            
    }


    /**
     * 页面版本Result转换 
     * @param cmsPageVersion    页面版本 
     */
    private CmsPageVersionQueryResult cmsPageVersionToResult(CmsPageVersion cmsPageVersion){
        
        CmsPageVersionQueryResult result = new CmsPageVersionQueryResult();
        result.setId(cmsPageVersion.getId());
        result.setCreateDate(cmsPageVersion.getCreateDate());
        result.setName(cmsPageVersion.getName());
        result.setOwner(cmsPageVersion.getOwner());
        result.setPageId(cmsPageVersion.getPageId());
        result.setVersionName(cmsPageVersion.getVersionName());
    
        return result;
            
    }


    /**
     * 页面版本DetailResult转换 
     * @param cmsPageVersion    页面版本 
     */
    private CmsPageVersionDetailResult cmsPageVersionToDetailResult(CmsPageVersion cmsPageVersion){
        
        CmsPageVersionDetailResult result = new CmsPageVersionDetailResult();
        result.setId(cmsPageVersion.getId());
        result.setCreateDate(cmsPageVersion.getCreateDate());
        result.setName(cmsPageVersion.getName());
        result.setOwner(cmsPageVersion.getOwner());
        result.setPageId(cmsPageVersion.getPageId());
        result.setContent(cmsPageVersion.getContent());
        result.setVersionName(cmsPageVersion.getVersionName());
    
        return result;
            
    }




}