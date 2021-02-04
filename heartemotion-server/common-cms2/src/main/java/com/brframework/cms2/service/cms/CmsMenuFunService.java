package com.brframework.cms2.service.cms;

import java.lang.*;
import java.util.*;
import com.brframework.cms2.entity.cms.*;
import org.springframework.data.domain.Page;
import com.brframework.commonweb.json.PageParam;
import com.brframework.cms2.pojo.cms.*;


/**
 * 菜单功能服务实现
 * @author xu
 * @date 2020-11-07 16:39:04
 */ 
public interface CmsMenuFunService{



    /**
     * 通过ID查询菜单功能 
     * @param cmsMenuFunId    菜单功能的ID 
     * @return 菜单功能
     */
     CmsMenuFun findCmsMenuFunById(Long cmsMenuFunId);

    /**
     * 查询菜单功能列表
     * @param menuSubId 子菜单ID
     * @return 菜单功能列表
     */
    List<CmsMenuFun> listCmsMenuFunByMenuSubId(Long menuSubId);

    /**
     * 添加菜单功能 
     * @param insertParam    添加参数 
     */
     void insertCmsMenuFun(CmsMenuFunInsertParamDTO insertParam);


    /**
     * 修改菜单功能 
     * @param cmsMenuFunId    菜单功能的ID
     * @param updateParam    修改参数 
     */
     void updateCmsMenuFun(Long cmsMenuFunId, CmsMenuFunUpdateParamDTO updateParam);


    /**
     * 删除菜单功能 
     * @param cmsMenuFunId    菜单功能的ID 
     */
     void removeCmsMenuFunById(Long cmsMenuFunId);




}