package com.brframework.cms2.service.cms.impl;

import java.lang.*;
import java.util.*;
import java.time.LocalDateTime;

import com.brframework.cms2.globals.CmsConstant;
import com.brframework.cms2.dao.cms.*;
import com.brframework.cms2.entity.cms.*;
import com.brframework.cms2.pojo.cms.*;
import com.brframework.cms2.service.cms.*;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.extern.slf4j.Slf4j;
import com.brframework.commondb.core.ExQuery;
import org.springframework.transaction.annotation.Transactional;
import com.querydsl.jpa.impl.JPAQueryFactory;
import javax.annotation.Resource;
import javax.persistence.EntityManager;


/**
 * 页面版本服务实现
 * @author xu
 * @date 2020-11-07 18:37:22
 */ 
@Service
@Slf4j
public class CmsPageVersionServiceImpl implements CmsPageVersionService{

    /** 页面版本Dao */
    @Autowired
    private CmsPageVersionDao cmsPageVersionDao;
    /** 页面Service */
    @Autowired
    public CmsPageService cmsPageService;

    /** QueryDSL查询 */
    private JPAQueryFactory queryFactory;


    /**
     * 设置QueryFactory 
     * @param entityManager    entity manager 
     */
    @Resource
    public void setQueryFactory(@Autowired EntityManager entityManager){
        
        queryFactory = new JPAQueryFactory(entityManager);
            
    }


    /**
     * 通过ID查询页面版本 
     * @param cmsPageVersionId    页面版本的ID 
     * @return 页面版本
     */
    @Override
    public CmsPageVersion findCmsPageVersionById(Long cmsPageVersionId){
        
        return cmsPageVersionDao.findById(cmsPageVersionId).orElse(null);
            
    }

    @Override
    public List<CmsPageVersion> listCmsPageVersionByPage(Long pageId) {
        return (List<CmsPageVersion>) cmsPageVersionDao.findAll(
                ExQuery.eq(QCmsPageVersion.cmsPageVersion.pageId, pageId)
                        .and(ExQuery.eq(QCmsPageVersion.cmsPageVersion.isDelete, CmsConstant.Delete.NORMAL)),
                QCmsPageVersion.cmsPageVersion.createDate.desc()
        );
    }



    /**
     * 添加页面版本 
     * @param insertParam    添加参数 
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertCmsPageVersion(CmsPageVersionInsertParamDTO insertParam){
        
        CmsPageVersion cmsPageVersion = new CmsPageVersion();
        cmsPageVersion.setCreateDate(LocalDateTime.now());
        cmsPageVersion.setName(insertParam.getName());
        cmsPageVersion.setOwner(insertParam.getOwner());
        cmsPageVersion.setPageId(insertParam.getPageId());
        cmsPageVersion.setContent(insertParam.getContent());
        cmsPageVersion.setVersionName(UUID.randomUUID().toString());
        cmsPageVersion.setIsDelete(CmsConstant.Delete.NORMAL);
    
        cmsPageVersionDao.save(cmsPageVersion);

        cmsPageService.updateContent(insertParam.getPageId(), insertParam.getContent());

    }


    /**
     * 删除页面版本 
     * @param cmsPageVersionId    页面版本的ID 
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeCmsPageVersionById(Long cmsPageVersionId){
        
        CmsPageVersion cmsPageVersion = findCmsPageVersionById(cmsPageVersionId);
        cmsPageVersion.setIsDelete(CmsConstant.Delete.DELETE);
        cmsPageVersionDao.save(cmsPageVersion);
            
    }




}