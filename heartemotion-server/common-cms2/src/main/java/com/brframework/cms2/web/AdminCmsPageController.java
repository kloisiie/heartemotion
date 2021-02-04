package com.brframework.cms2.web;

import java.lang.*;
import java.util.*;
import java.util.stream.Collectors;

import com.brframework.cms2.entity.cms.*;
import com.brframework.cms2.pojo.cms.*;
import com.brframework.cms2.service.cms.*;
import com.brframework.cms2.json.admin.cms.*;
import com.google.common.collect.Lists;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.extern.slf4j.Slf4j;
import com.brframework.commonweb.json.JSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import javax.validation.Valid;


/**
 * 页面模块
 * @author xu
 * @date 2020-11-07 18:03:28
 */ 
@RestController
@Api(tags = "页面模块")
@RequestMapping("/admin/access")
@Slf4j
public class AdminCmsPageController{

    /** 页面Service */
    @Autowired
    public CmsPageService cmsPageService;


    /** 页面组Service */
    @Autowired
    public CmsPageGroupService cmsPageGroupService;

    
    @ApiOperation(value = "页面组列表", notes = "页面组列表")
    @GetMapping("/v1/cms-page-group/list")
    //@PreAuthorize("hasRole('cms-page-group-query')")
    public JSONResult<List<CmsPageGroupQueryResult>> cmsGroupListV1(){

        return JSONResult.ok(cmsPageGroupService.listCmsPageGroup().stream().parallel()
                .map(this::cmsPageGroupToResult)
                .collect(Collectors.toList())
        );

    }


    @ApiOperation(value = "添加页面组", notes = "添加页面组")
    @PostMapping("/v1/cms-page-group/add")
    //@PreAuthorize("hasRole('cms-page-group-add')")
    public JSONResult cmsPageGroupAddV1(@Valid CmsPageGroupInsertParam insertParam){

        CmsPageGroupInsertParamDTO dtoParam = new CmsPageGroupInsertParamDTO();
        dtoParam.setName(insertParam.getName());
        dtoParam.setSort(insertParam.getSort());

        cmsPageGroupService.insertCmsPageGroup(dtoParam);
        return JSONResult.ok();

    }



    @ApiOperation(value = "修改页面组", notes = "修改页面组")
    @PostMapping("/v1/cms-page-group/update/{pageGroupId}")
    //@PreAuthorize("hasRole('cms-page-group-update')")
    public JSONResult cmsPageGroupUpdateV1(@PathVariable Long pageGroupId, @Valid CmsPageGroupUpdateParam updateParam){

        CmsPageGroupUpdateParamDTO dtoParam = new CmsPageGroupUpdateParamDTO();
        dtoParam.setName(updateParam.getName());
        dtoParam.setSort(updateParam.getSort());

        cmsPageGroupService.updateCmsPageGroup(pageGroupId, dtoParam);
        return JSONResult.ok();

    }



    @ApiOperation(value = "删除页面组", notes = "删除页面组")
    @DeleteMapping("/v1/cms-page-group/del/{pageGroupId}")
    //@PreAuthorize("hasRole('cms-page-group-del')")
    public JSONResult cmsPageGroupDeleteV1(@PathVariable Long pageGroupId){

        cmsPageGroupService.removeCmsPageGroupById(pageGroupId);
        return JSONResult.ok();

    }




    @ApiOperation(value = "页面详情", notes = "页面详情")
    @GetMapping("/v1/cms-page/details/{pageId}")
    //@PreAuthorize("hasRole('cms-page-query')")
    public JSONResult<CmsPageDetailResult> cmsPageDetailsV1(@PathVariable Long pageId){
        
        return JSONResult.ok(cmsPageToDetailResult(cmsPageService.findCmsPageById(pageId)));
            
    }

    @ApiOperation(value = "页面详情(路由名称)", notes = "页面详情(路由名称)")
    @GetMapping("/v1/cms-page/details/route-name/{routeName}")
    //@PreAuthorize("hasRole('cms-page-query')")
    public JSONResult<CmsPageDetailResult> cmsPageDetailsByRouteNameV1(@PathVariable String routeName){

        return JSONResult.ok(cmsPageToDetailResult(cmsPageService.findCmsPageByRouteName(routeName)));

    }


    
    @ApiOperation(value = "添加页面", notes = "添加页面")
    @PostMapping("/v1/cms-page/add")
    //@PreAuthorize("hasRole('cms-page-add')")
    public JSONResult cmsPageAddV1(@Valid CmsPageInsertParam insertParam){

        CmsPageInsertParamDTO dtoParam = new CmsPageInsertParamDTO();
        dtoParam.setName(insertParam.getName());
        dtoParam.setType(insertParam.getType());
        dtoParam.setGroupId(insertParam.getGroupId());
        dtoParam.setSort(insertParam.getSort());
        dtoParam.setRoute(insertParam.getRoute());
    
        cmsPageService.insertCmsPage(dtoParam);
        return JSONResult.ok();
            
    }


    
    @ApiOperation(value = "修改页面", notes = "修改页面")
    @PostMapping("/v1/cms-page/update/{pageId}")
    //@PreAuthorize("hasRole('cms-page-update')")
    public JSONResult cmsPageUpdateV1(@PathVariable Long pageId,@Valid CmsPageUpdateParam updateParam){
        
        CmsPageUpdateParamDTO dtoParam = new CmsPageUpdateParamDTO();
        dtoParam.setName(updateParam.getName());
        dtoParam.setType(updateParam.getType());
        dtoParam.setGroupId(updateParam.getGroupId());
        dtoParam.setSort(updateParam.getSort());
        dtoParam.setRoute(updateParam.getRoute());
    
        cmsPageService.updateCmsPage(pageId, dtoParam);
        return JSONResult.ok();
            
    }


    
    @ApiOperation(value = "删除页面", notes = "删除页面")
    @DeleteMapping("/v1/cms-page/del/{pageId}")
    //@PreAuthorize("hasRole('cms-page-del')")
    public JSONResult cmsPageDeleteV1(@PathVariable Long pageId){
        
        cmsPageService.removeCmsPageById(pageId);
        return JSONResult.ok();
            
    }

    /**
     * 页面组Result转换
     * @param cmsPageGroup    页面组
     */
    private CmsPageGroupQueryResult cmsPageGroupToResult(CmsPageGroup cmsPageGroup){

        CmsPageGroupQueryResult result = new CmsPageGroupQueryResult();
        result.setId(cmsPageGroup.getId());
        result.setCreateDate(cmsPageGroup.getCreateDate());
        result.setName(cmsPageGroup.getName());
        result.setSort(cmsPageGroup.getSort());
        List<CmsPageQueryResult> pageList = Lists.newArrayList();

        for (CmsPage cmsPage : cmsPageService.listCmsPageByGroup(cmsPageGroup.getId())) {
            pageList.add(cmsPageToResult(cmsPage));
        }

        result.setPageList(pageList);

        return result;
    }

    /**
     * 页面Result转换 
     * @param cmsPage    页面 
     */
    private CmsPageQueryResult cmsPageToResult(CmsPage cmsPage){
        
        CmsPageQueryResult result = new CmsPageQueryResult();
        result.setId(cmsPage.getId());
        result.setCreateDate(cmsPage.getCreateDate());
        result.setName(cmsPage.getName());
        result.setType(cmsPage.getType());
        result.setGroupId(cmsPage.getGroupId());
        result.setSort(cmsPage.getSort());
        result.setRoute(cmsPage.getRoute());
    
        return result;
            
    }


    /**
     * 页面DetailResult转换 
     * @param cmsPage    页面 
     */
    private CmsPageDetailResult cmsPageToDetailResult(CmsPage cmsPage){
        
        CmsPageDetailResult result = new CmsPageDetailResult();
        result.setId(cmsPage.getId());
        result.setCreateDate(cmsPage.getCreateDate());
        result.setName(cmsPage.getName());
        result.setType(cmsPage.getType());
        result.setGroupId(cmsPage.getGroupId());
        result.setRouteName(cmsPage.getRouteName());
        result.setSort(cmsPage.getSort());
        result.setRoute(cmsPage.getRoute());
        result.setContent(cmsPage.getContent());
    
        return result;
    }
}