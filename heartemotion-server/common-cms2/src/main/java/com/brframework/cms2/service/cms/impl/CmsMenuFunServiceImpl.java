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
import com.brframework.commondb.core.ExQuery;
import org.springframework.transaction.annotation.Transactional;
import com.querydsl.jpa.impl.JPAQueryFactory;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import com.querydsl.core.QueryResults;


/**
 * 菜单功能服务实现
 * @author xu
 * @date 2020-11-07 16:39:04
 */ 
@Service
@Slf4j
public class CmsMenuFunServiceImpl implements CmsMenuFunService{

    /** 菜单功能Dao */
    @Autowired
    private CmsMenuFunDao cmsMenuFunDao;
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
     * 通过ID查询菜单功能 
     * @param cmsMenuFunId    菜单功能的ID 
     * @return 菜单功能
     */
    @Override
    public CmsMenuFun findCmsMenuFunById(Long cmsMenuFunId){
        
        return cmsMenuFunDao.findById(cmsMenuFunId).orElse(null);
            
    }

    @Override
    public List<CmsMenuFun> listCmsMenuFunByMenuSubId(Long menuSubId) {
        return (List<CmsMenuFun>) cmsMenuFunDao.findAll(
                ExQuery.eq(QCmsMenuFun.cmsMenuFun.menuSubId, menuSubId),
                QCmsMenuFun.cmsMenuFun.sort.asc()
        );
    }


    /**
     * 添加菜单功能 
     * @param insertParam    添加参数 
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertCmsMenuFun(CmsMenuFunInsertParamDTO insertParam){
        
        CmsMenuFun cmsMenuFun = new CmsMenuFun();
        cmsMenuFun.setCreateDate(LocalDateTime.now());
        cmsMenuFun.setName(insertParam.getName());
        cmsMenuFun.setMenuSubId(insertParam.getMenuSubId());
        cmsMenuFun.setSort(insertParam.getSort());
        cmsMenuFun.setRole(insertParam.getRole());
    
        cmsMenuFunDao.save(cmsMenuFun);
            
    }


    /**
     * 修改菜单功能 
     * @param cmsMenuFunId    菜单功能的ID
     * @param updateParam    修改参数 
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateCmsMenuFun(Long cmsMenuFunId, CmsMenuFunUpdateParamDTO updateParam){
        
        CmsMenuFun cmsMenuFun = findCmsMenuFunById(cmsMenuFunId);
        cmsMenuFun.setName(updateParam.getName());
        cmsMenuFun.setMenuSubId(updateParam.getMenuSubId());
        cmsMenuFun.setSort(updateParam.getSort());
        cmsMenuFun.setRole(updateParam.getRole());
    
        cmsMenuFunDao.save(cmsMenuFun);
            
    }


    /**
     * 删除菜单功能 
     * @param cmsMenuFunId    菜单功能的ID 
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeCmsMenuFunById(Long cmsMenuFunId){
        
        CmsMenuFun cmsMenuFun = findCmsMenuFunById(cmsMenuFunId);
        cmsMenuFunDao.delete(cmsMenuFun);
            
    }




}