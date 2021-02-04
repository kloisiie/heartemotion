package com.brframework.cms2.service.cms;

import java.lang.*;
import java.util.*;
import com.brframework.cms2.entity.cms.*;
import org.springframework.data.domain.Page;
import com.brframework.commonweb.json.PageParam;
import com.brframework.cms2.pojo.cms.*;


/**
 * 页面服务实现
 * @author xu
 * @date 2020-11-07 17:52:25
 */ 
public interface CmsPageService{



    /**
     * 通过ID查询页面 
     * @param cmsPageId    页面的ID 
     * @return 页面
     */
     CmsPage findCmsPageById(Long cmsPageId);


    /**
     * 通过路由名称查询页面
     * @param routeName   路由名称
     * @return   页面
     */
     CmsPage findCmsPageByRouteName(String routeName);


    /**
     * 查询页面
     * @param groupId  组ID
     * @return  页面列表
     */
     List<CmsPage> listCmsPageByGroup(Long groupId);


    /**
     * 添加页面 
     * @param insertParam    添加参数 
     */
     void insertCmsPage(CmsPageInsertParamDTO insertParam);


    /**
     * 修改页面 
     * @param updateParam    修改参数
     * @param cmsPageId    页面的ID 
     */
     void updateCmsPage(Long cmsPageId, CmsPageUpdateParamDTO updateParam);


    /**
     * 修改页面content
     * @param cmsPageId  页面id
     * @param content    页面内容
     */
     void updateContent(Long cmsPageId, String content);


    /**
     * 删除页面 
     * @param cmsPageId    页面的ID 
     */
     void removeCmsPageById(Long cmsPageId);




}