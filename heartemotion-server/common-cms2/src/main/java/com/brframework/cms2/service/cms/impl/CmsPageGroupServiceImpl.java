package com.brframework.cms2.service.cms.impl;

import java.lang.*;
import java.util.*;
import java.time.LocalDateTime;
import org.springframework.data.domain.*;
import com.brframework.commonweb.json.PageParam;
import com.brframework.cms2.dao.cms.*;
import com.brframework.cms2.entity.cms.*;
import com.brframework.cms2.pojo.cms.*;
import com.brframework.cms2.service.cms.*;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.extern.slf4j.Slf4j;
import com.brframework.commondb.utils.QueryUtils;
import com.brframework.commondb.core.ExQuery;
import org.springframework.transaction.annotation.Transactional;
import com.querydsl.jpa.impl.JPAQueryFactory;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import com.querydsl.core.QueryResults;


/**
 * 页面组服务实现
 * @author xu
 * @date 2020-11-07 17:44:27
 */ 
@Service
@Slf4j
public class CmsPageGroupServiceImpl implements CmsPageGroupService{

    /** 页面组Dao */
    @Autowired
    private CmsPageGroupDao cmsPageGroupDao;
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
     * 通过ID查询页面组 
     * @param cmsPageGroupId    页面组的ID 
     * @return 页面组
     */
    @Override
    public CmsPageGroup findCmsPageGroupById(Long cmsPageGroupId){
        
        return cmsPageGroupDao.findById(cmsPageGroupId).orElse(null);
            
    }

    @Override
    public List<CmsPageGroup> listCmsPageGroup() {
        return (List<CmsPageGroup>) cmsPageGroupDao.findAll(
                QCmsPageGroup.cmsPageGroup.sort.asc()
        );
    }


    /**
     * 添加页面组 
     * @param insertParam    添加参数 
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertCmsPageGroup(CmsPageGroupInsertParamDTO insertParam){
        
        CmsPageGroup cmsPageGroup = new CmsPageGroup();
        cmsPageGroup.setCreateDate(LocalDateTime.now());
        cmsPageGroup.setName(insertParam.getName());
        cmsPageGroup.setSort(insertParam.getSort());
    
        cmsPageGroupDao.save(cmsPageGroup);
            
    }


    /**
     * 修改页面组 
     * @param updateParam    修改参数
     * @param cmsPageGroupId    页面组的ID 
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateCmsPageGroup(Long cmsPageGroupId, CmsPageGroupUpdateParamDTO updateParam){
        
        CmsPageGroup cmsPageGroup = findCmsPageGroupById(cmsPageGroupId);
        cmsPageGroup.setName(updateParam.getName());
        cmsPageGroup.setSort(updateParam.getSort());
    
        cmsPageGroupDao.save(cmsPageGroup);
            
    }


    /**
     * 删除页面组 
     * @param cmsPageGroupId    页面组的ID 
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeCmsPageGroupById(Long cmsPageGroupId){
        
        CmsPageGroup cmsPageGroup = findCmsPageGroupById(cmsPageGroupId);
        cmsPageGroupDao.delete(cmsPageGroup);
            
    }




}