package com.brframework.cms2.service.cms;

import java.lang.*;
import java.util.*;
import com.brframework.cms2.entity.cms.*;
import org.springframework.data.domain.Page;
import com.brframework.commonweb.json.PageParam;
import com.brframework.cms2.pojo.cms.*;


/**
 * 菜单服务实现
 * @author xu
 * @date 2020-11-07 16:34:16
 */ 
public interface CmsMenuService{

    /**
     * 通过ID查询菜单 
     * @param cmsMenuId    菜单的ID 
     * @return 菜单
     */
     CmsMenu findCmsMenuById(Long cmsMenuId);


    /**
     * 查询菜单列表
     * @return 菜单列表
     */
     List<CmsMenu> listCmsMenu();


    /**
     * 添加菜单 
     * @param insertParam    添加参数 
     */
     void insertCmsMenu(CmsMenuInsertParamDTO insertParam);


    /**
     * 修改菜单 
     * @param updateParam    修改参数
     * @param cmsMenuId    菜单的ID 
     */
     void updateCmsMenu(Long cmsMenuId, CmsMenuUpdateParamDTO updateParam);


    /**
     * 删除菜单 
     * @param cmsMenuId    菜单的ID 
     */
     void removeCmsMenuById(Long cmsMenuId);




}