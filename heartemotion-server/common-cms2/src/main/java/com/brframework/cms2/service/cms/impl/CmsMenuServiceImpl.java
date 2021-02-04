package com.brframework.cms2.service.cms.impl;

import java.lang.*;
import java.util.*;
import java.time.LocalDateTime;

import com.brframework.cms2.core.CmsDict;
import com.brframework.cms2.json.admin.cms.CmsDictEntry;
import com.google.common.collect.Lists;
import org.springframework.data.domain.*;
import com.brframework.commonweb.json.PageParam;
import com.brframework.cms2.dao.cms.*;
import com.brframework.cms2.entity.cms.*;
import com.brframework.cms2.pojo.cms.*;
import com.brframework.cms2.service.cms.*;
import org.springframework.stereotype.Component;
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
 * 菜单服务实现
 * @author xu
 * @date 2020-11-07 16:34:16
 */ 
@Service
@Slf4j
public class CmsMenuServiceImpl implements CmsMenuService{

    /** 菜单Dao */
    @Autowired
    private CmsMenuDao cmsMenuDao;
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
     * 通过ID查询菜单 
     * @param cmsMenuId    菜单的ID 
     * @return 菜单
     */
    @Override
    public CmsMenu findCmsMenuById(Long cmsMenuId){
        
        return cmsMenuDao.findById(cmsMenuId).orElse(null);
            
    }

    @Override
    public List<CmsMenu> listCmsMenu() {
        return (List<CmsMenu>) cmsMenuDao.findAll(
                QCmsMenu.cmsMenu.sort.asc()
        );
    }

    /**
     * 添加菜单 
     * @param insertParam    添加参数 
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertCmsMenu(CmsMenuInsertParamDTO insertParam){
        
        CmsMenu cmsMenu = new CmsMenu();
        cmsMenu.setCreateDate(LocalDateTime.now());
        cmsMenu.setName(insertParam.getName());
        cmsMenu.setIcon(insertParam.getIcon());
        cmsMenu.setSort(insertParam.getSort());
        cmsMenu.setType(insertParam.getType());
        cmsMenu.setHide(insertParam.getHide());
    
        cmsMenuDao.save(cmsMenu);
            
    }


    /**
     * 修改菜单 
     * @param updateParam    修改参数
     * @param cmsMenuId    菜单的ID 
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateCmsMenu(Long cmsMenuId, CmsMenuUpdateParamDTO updateParam){
        
        CmsMenu cmsMenu = findCmsMenuById(cmsMenuId);
        cmsMenu.setName(updateParam.getName());
        cmsMenu.setIcon(updateParam.getIcon());
        cmsMenu.setSort(updateParam.getSort());
        cmsMenu.setType(updateParam.getType());
        cmsMenu.setHide(updateParam.getHide());
    
        cmsMenuDao.save(cmsMenu);
            
    }


    /**
     * 删除菜单 
     * @param cmsMenuId    菜单的ID 
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeCmsMenuById(Long cmsMenuId){
        
        CmsMenu cmsMenu = findCmsMenuById(cmsMenuId);
        cmsMenuDao.delete(cmsMenu);
            
    }




}