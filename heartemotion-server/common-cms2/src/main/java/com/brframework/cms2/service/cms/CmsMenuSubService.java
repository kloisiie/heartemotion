package com.brframework.cms2.service.cms;

import java.lang.*;
import java.util.*;
import com.brframework.cms2.entity.cms.*;
import org.springframework.data.domain.Page;
import com.brframework.commonweb.json.PageParam;
import com.brframework.cms2.pojo.cms.*;


/**
 * 子菜单服务实现
 * @author xu
 * @date 2020-11-07 16:37:37
 */ 
public interface CmsMenuSubService{



    /**
     * 通过ID查询子菜单 
     * @param cmsMenuSubId    子菜单的ID 
     * @return 子菜单
     */
     CmsMenuSub findCmsMenuSubById(Long cmsMenuSubId);

    /**
     * 查询子菜单列表
     * @param menuId 主菜单ID
     * @return 子菜单列表
     */
    List<CmsMenuSub> listCmsMenuSubByMenuId(Long menuId);

    /**
     * 添加子菜单 
     * @param insertParam    添加参数 
     */
     void insertCmsMenuSub(CmsMenuSubInsertParamDTO insertParam);


    /**
     * 修改子菜单 
     * @param updateParam    修改参数
     * @param cmsMenuSubId    子菜单的ID 
     */
     void updateCmsMenuSub(Long cmsMenuSubId, CmsMenuSubUpdateParamDTO updateParam);


    /**
     * 删除子菜单 
     * @param cmsMenuSubId    子菜单的ID 
     */
     void removeCmsMenuSubById(Long cmsMenuSubId);




}