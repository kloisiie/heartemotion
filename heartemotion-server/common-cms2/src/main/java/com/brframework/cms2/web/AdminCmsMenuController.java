package com.brframework.cms2.web;

import java.lang.*;
import java.util.*;
import java.util.stream.Collectors;

import com.brframework.cms2.core.CmsDict;
import com.brframework.cms2.entity.cms.*;
import com.brframework.cms2.pojo.cms.*;
import com.brframework.cms2.service.cms.*;
import com.brframework.cms2.json.admin.cms.*;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.extern.slf4j.Slf4j;
import com.brframework.commonweb.json.JSONResult;
import com.brframework.commonweb.json.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import javax.validation.Valid;


/**
 * 菜单模块
 * @author xu
 * @date 2020-11-07 16:34:16
 */ 
@RestController
@Api(tags = "菜单模块")
@RequestMapping("/admin/access")
@Slf4j
public class AdminCmsMenuController{

    public static final String OPTION_MENU_FUN = "OPTION_MENU_FUN";

    /** 菜单Service */
    @Autowired
    public CmsMenuService cmsMenuService;

    /** 菜单功能Service */
    @Autowired
    public CmsMenuFunService cmsMenuFunService;

    /** 子菜单Service */
    @Autowired
    public CmsMenuSubService cmsMenuSubService;

    /** 页面Service */
    @Autowired
    public CmsPageService cmsPageService;

    @ApiOperation(value = "菜单列表", notes = "菜单列表")
    @GetMapping("/v1/cms-menu/list")
    //@PreAuthorize("hasRole('cms-menu-query')")
    public JSONResult<List<CmsMenuQueryResult>> cmsMenuListV1(){
        return JSONResult.ok(cmsMenuService.listCmsMenu().stream().parallel()
                .map(this::cmsMenuToResult)
                .collect(Collectors.toList())
        );
    }

    
    @ApiOperation(value = "添加菜单", notes = "添加菜单")
    @PostMapping("/v1/cms-menu/add")
    //@PreAuthorize("hasRole('cms-menu-add')")
    public JSONResult cmsMenuAddV1(@Valid CmsMenuInsertParam insertParam){
        
        CmsMenuInsertParamDTO dtoParam = new CmsMenuInsertParamDTO();
        dtoParam.setName(insertParam.getName());
        dtoParam.setIcon(insertParam.getIcon());
        dtoParam.setSort(insertParam.getSort());
        dtoParam.setType(insertParam.getType());
        dtoParam.setHide(insertParam.getHide());
    
        cmsMenuService.insertCmsMenu(dtoParam);
        return JSONResult.ok();
            
    }


    
    @ApiOperation(value = "修改菜单", notes = "修改菜单")
    @PostMapping("/v1/cms-menu/update/{menuId}")
    //@PreAuthorize("hasRole('cms-menu-update')")
    public JSONResult cmsMenuUpdateV1(@PathVariable Long menuId, @Valid CmsMenuUpdateParam updateParam){
        
        CmsMenuUpdateParamDTO dtoParam = new CmsMenuUpdateParamDTO();
        dtoParam.setName(updateParam.getName());
        dtoParam.setIcon(updateParam.getIcon());
        dtoParam.setSort(updateParam.getSort());
        dtoParam.setType(updateParam.getType());
        dtoParam.setHide(updateParam.getHide());
    
        cmsMenuService.updateCmsMenu(menuId, dtoParam);
        return JSONResult.ok();
            
    }


    
    @ApiOperation(value = "删除菜单", notes = "删除菜单")
    @DeleteMapping("/v1/cms-menu/del/{menuId}")
    //@PreAuthorize("hasRole('cms-menu-del')")
    public JSONResult cmsMenuDeleteV1(@PathVariable Long menuId){
        
        cmsMenuService.removeCmsMenuById(menuId);
        return JSONResult.ok();
    }


    @ApiOperation(value = "添加菜单功能", notes = "添加菜单功能")
    @PostMapping("/v1/cms-menu-fun/add")
    //@PreAuthorize("hasRole('cms-menu-fun-add')")
    public JSONResult cmsMenuFunAddV1( @Valid CmsMenuFunInsertParam insertParam){

        CmsMenuFunInsertParamDTO dtoParam = new CmsMenuFunInsertParamDTO();
        dtoParam.setName(insertParam.getName());
        dtoParam.setMenuSubId(insertParam.getMenuSubId());
        dtoParam.setSort(insertParam.getSort());
        dtoParam.setRole(insertParam.getRole());

        cmsMenuFunService.insertCmsMenuFun(dtoParam);
        return JSONResult.ok();

    }

    @ApiOperation(value = "修改菜单功能", notes = "修改菜单功能")
    @PostMapping("/v1/cms-menu-fun/update/{menuFunId}")
    //@PreAuthorize("hasRole('cms-menu-fun-update')")
    public JSONResult cmsMenuFunUpdateV1(@PathVariable Long menuFunId, @Valid CmsMenuFunUpdateParam updateParam){

        CmsMenuFunUpdateParamDTO dtoParam = new CmsMenuFunUpdateParamDTO();
        dtoParam.setName(updateParam.getName());
        dtoParam.setMenuSubId(updateParam.getMenuSubId());
        dtoParam.setSort(updateParam.getSort());
        dtoParam.setRole(updateParam.getRole());

        cmsMenuFunService.updateCmsMenuFun(menuFunId, dtoParam);
        return JSONResult.ok();

    }

    @ApiOperation(value = "删除菜单功能", notes = "删除菜单功能")
    @DeleteMapping("/v1/cms-menu-fun/del/{menuFunId}")
    //@PreAuthorize("hasRole('cms-menu-fun-del')")
    public JSONResult cmsMenuFunDeleteV1(@PathVariable Long menuFunId){

        cmsMenuFunService.removeCmsMenuFunById(menuFunId);
        return JSONResult.ok();

    }





    @ApiOperation(value = "添加子菜单", notes = "添加子菜单")
    @PostMapping("/v1/cms-menu-sub/add")
    //@PreAuthorize("hasRole('cms-menu-sub-add')")
    public JSONResult cmsMenuSubAddV1(@Valid CmsMenuSubInsertParam insertParam){

        CmsMenuSubInsertParamDTO dtoParam = new CmsMenuSubInsertParamDTO();
        dtoParam.setName(insertParam.getName());
        dtoParam.setIcon(insertParam.getIcon());
        dtoParam.setSort(insertParam.getSort());
        dtoParam.setMenuId(insertParam.getMenuId());
        dtoParam.setPageId(insertParam.getPageId()[1]);
        dtoParam.setRole(insertParam.getRole());
        dtoParam.setHide(insertParam.getHide());

        cmsMenuSubService.insertCmsMenuSub(dtoParam);
        return JSONResult.ok();

    }



    @ApiOperation(value = "修改子菜单", notes = "修改子菜单")
    @PostMapping("/v1/cms-menu-sub/update/{subMenuId}")
    //@PreAuthorize("hasRole('cms-menu-sub-update')")
    public JSONResult cmsMenuSubUpdateV1(@PathVariable Long subMenuId, @Valid CmsMenuSubUpdateParam updateParam){

        CmsMenuSubUpdateParamDTO dtoParam = new CmsMenuSubUpdateParamDTO();
        dtoParam.setName(updateParam.getName());
        dtoParam.setIcon(updateParam.getIcon());
        dtoParam.setSort(updateParam.getSort());
        dtoParam.setMenuId(updateParam.getMenuId());
        dtoParam.setPageId(updateParam.getPageId()[1]);
        dtoParam.setRole(updateParam.getRole());
        dtoParam.setHide(updateParam.getHide());

        cmsMenuSubService.updateCmsMenuSub(subMenuId, dtoParam);
        return JSONResult.ok();

    }



    @ApiOperation(value = "删除子菜单", notes = "删除子菜单")
    @DeleteMapping("/v1/cms-menu-sub/del/{subMenuId}")
    //@PreAuthorize("hasRole('cms-menu-sub-del')")
    public JSONResult cmsMenuSubDeleteV1(@PathVariable Long subMenuId){
        cmsMenuSubService.removeCmsMenuSubById(subMenuId);
        return JSONResult.ok();

    }

    /**
     * 菜单Result转换
     * @param cmsMenu    菜单功
     */
    private CmsMenuQueryResult cmsMenuToResult(CmsMenu cmsMenu){

        CmsMenuQueryResult result = new CmsMenuQueryResult();
        result.setId(cmsMenu.getId());
        result.setCreateDate(cmsMenu.getCreateDate());
        result.setType(cmsMenu.getType());
        result.setSort(cmsMenu.getSort());
        result.setIcon(cmsMenu.getIcon());
        result.setName(cmsMenu.getName());
        result.setHide(cmsMenu.getHide());
        List<CmsMenuSubQueryResult> menuSubList = Lists.newArrayList();

        for (CmsMenuSub cmsMenuSub : cmsMenuSubService.listCmsMenuSubByMenuId(cmsMenu.getId())) {
            CmsMenuSubQueryResult subResult = new CmsMenuSubQueryResult();
            subResult.setId(cmsMenuSub.getId());
            subResult.setCreateDate(cmsMenuSub.getCreateDate());
            subResult.setName(cmsMenuSub.getName());
            subResult.setMenuId(cmsMenuSub.getMenuId());
            subResult.setIcon(cmsMenuSub.getIcon());
            subResult.setSort(cmsMenuSub.getSort());
            CmsPage page = cmsPageService.findCmsPageById(cmsMenuSub.getPageId());
            subResult.setPageId(new Long[]{page.getGroupId(), page.getId()});
            subResult.setPageName(page.getName());
            subResult.setHide(cmsMenuSub.getHide());
            subResult.setRole(cmsMenuSub.getRole());

            List<CmsMenuFunQueryResult> menuFunList = Lists.newArrayList();
            for (CmsMenuFun cmsMenuFun : cmsMenuFunService.listCmsMenuFunByMenuSubId(cmsMenuSub.getId())) {
                CmsMenuFunQueryResult funResult = new CmsMenuFunQueryResult();
                funResult.setId(cmsMenuFun.getId());
                funResult.setCreateDate(cmsMenuFun.getCreateDate());
                funResult.setName(cmsMenuFun.getName());
                funResult.setMenuSubId(cmsMenuFun.getMenuSubId());
                funResult.setSort(cmsMenuFun.getSort());
                funResult.setRole(cmsMenuFun.getRole());
                menuFunList.add(funResult);
            }

            subResult.setChildren(menuFunList);
            menuSubList.add(subResult);
        }
        result.setChildren(menuSubList);

        return result;

    }


    @Component
    class MenuFunDict implements CmsDict {

        @Override
        public String dictKey() {
            return OPTION_MENU_FUN;
        }

        @Override
        public List<CmsDictEntry> dictEntry(String... childKeys) {
            List<CmsDictEntry> entries = Lists.newArrayList();
            entries.add(new CmsDictEntry("1-*", "超级管理员",
                    Lists.newArrayList(new CmsDictEntry("2-*", "超级管理员",
                            Lists.newArrayList(new CmsDictEntry("*", "超级管理员"))))));
            for (CmsMenu menu : cmsMenuService.listCmsMenu()) {
                List<CmsDictEntry> subMenuEntry = Lists.newArrayList();
                for (CmsMenuSub menuSub : cmsMenuSubService.listCmsMenuSubByMenuId(menu.getId())) {
                    List<CmsDictEntry> menuFunEntry = Lists.newArrayList();
                    subMenuEntry.add(new CmsDictEntry("2-" + menuSub.getId(), menuSub.getName(), menuFunEntry));
                    for (CmsMenuFun cmsMenuFun : cmsMenuFunService.listCmsMenuFunByMenuSubId(menuSub.getId())) {
                        menuFunEntry.add(new CmsDictEntry(cmsMenuFun.getRole(), cmsMenuFun.getName()));
                    }
                }
                entries.add(new CmsDictEntry("1-" + menu.getId(), menu.getName(), subMenuEntry));
            }
            return entries;
        }

    }
}