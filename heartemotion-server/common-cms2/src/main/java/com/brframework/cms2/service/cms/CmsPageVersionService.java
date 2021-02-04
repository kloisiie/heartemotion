package com.brframework.cms2.service.cms;

import java.lang.*;
import java.util.*;
import com.brframework.cms2.entity.cms.*;
import org.springframework.data.domain.Page;
import com.brframework.commonweb.json.PageParam;
import com.brframework.cms2.pojo.cms.*;


/**
 * 页面版本服务实现
 * @author xu
 * @date 2020-11-07 18:37:22
 */ 
public interface CmsPageVersionService{



    /**
     * 通过ID查询页面版本 
     * @param cmsPageVersionId    页面版本的ID 
     * @return 页面版本
     */
     CmsPageVersion findCmsPageVersionById(Long cmsPageVersionId);

    /**
     * 查询页面版本列表
     * @param pageId  页面ID
     * @return  版本列表
     */
     List<CmsPageVersion> listCmsPageVersionByPage(Long pageId);

    /**
     * 添加页面版本 
     * @param insertParam    添加参数 
     */
     void insertCmsPageVersion(CmsPageVersionInsertParamDTO insertParam);


    /**
     * 删除页面版本 
     * @param cmsPageVersionId    页面版本的ID 
     */
     void removeCmsPageVersionById(Long cmsPageVersionId);




}