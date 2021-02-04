package com.brframework.cms2.service.cms;

import java.lang.*;
import java.util.*;
import com.brframework.cms2.entity.cms.*;
import org.springframework.data.domain.Page;
import com.brframework.commonweb.json.PageParam;
import com.brframework.cms2.pojo.cms.*;


/**
 * 页面组服务实现
 * @author xu
 * @date 2020-11-07 17:44:27
 */ 
public interface CmsPageGroupService{



    /**
     * 通过ID查询页面组 
     * @param cmsPageGroupId    页面组的ID 
     * @return 页面组
     */
     CmsPageGroup findCmsPageGroupById(Long cmsPageGroupId);

    /**
     * 查询页面组
     * @return  页面组列表
     */
     List<CmsPageGroup> listCmsPageGroup();

    /**
     * 添加页面组 
     * @param insertParam    添加参数 
     */
     void insertCmsPageGroup(CmsPageGroupInsertParamDTO insertParam);


    /**
     * 修改页面组 
     * @param updateParam    修改参数
     * @param cmsPageGroupId    页面组的ID 
     */
     void updateCmsPageGroup(Long cmsPageGroupId, CmsPageGroupUpdateParamDTO updateParam);


    /**
     * 删除页面组 
     * @param cmsPageGroupId    页面组的ID 
     */
     void removeCmsPageGroupById(Long cmsPageGroupId);




}