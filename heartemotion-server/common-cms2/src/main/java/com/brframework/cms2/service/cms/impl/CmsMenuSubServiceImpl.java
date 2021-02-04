package com.brframework.cms2.service.cms.impl;

import java.lang.*;
import java.util.*;
import java.time.LocalDateTime;
import com.brframework.cms2.dao.cms.*;
import com.brframework.cms2.entity.cms.*;
import com.brframework.cms2.pojo.cms.*;
import com.brframework.cms2.service.cms.*;
import com.brframework.commondb.core.ExQuery;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import com.querydsl.jpa.impl.JPAQueryFactory;
import javax.annotation.Resource;
import javax.persistence.EntityManager;


/**
 * 子菜单服务实现
 * @author xu
 * @date 2020-11-07 16:37:38
 */ 
@Service
@Slf4j
public class CmsMenuSubServiceImpl implements CmsMenuSubService{

    /** 子菜单Dao */
    @Autowired
    private CmsMenuSubDao cmsMenuSubDao;
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
     * 通过ID查询子菜单 
     * @param cmsMenuSubId    子菜单的ID 
     * @return 子菜单
     */
    @Override
    public CmsMenuSub findCmsMenuSubById(Long cmsMenuSubId){
        
        return cmsMenuSubDao.findById(cmsMenuSubId).orElse(null);
            
    }

    @Override
    public List<CmsMenuSub> listCmsMenuSubByMenuId(Long menuId) {
        return (List<CmsMenuSub>) cmsMenuSubDao.findAll(
                ExQuery.eq(QCmsMenuSub.cmsMenuSub.menuId, menuId),
                QCmsMenuSub.cmsMenuSub.sort.asc()
        );
    }


    /**
     * 添加子菜单 
     * @param insertParam    添加参数 
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertCmsMenuSub(CmsMenuSubInsertParamDTO insertParam){
        
        CmsMenuSub cmsMenuSub = new CmsMenuSub();
        cmsMenuSub.setCreateDate(LocalDateTime.now());
        cmsMenuSub.setName(insertParam.getName());
        cmsMenuSub.setIcon(insertParam.getIcon());
        cmsMenuSub.setSort(insertParam.getSort());
        cmsMenuSub.setMenuId(insertParam.getMenuId());
        cmsMenuSub.setPageId(insertParam.getPageId());
        cmsMenuSub.setRole(insertParam.getRole());
        cmsMenuSub.setHide(insertParam.getHide());
    
        cmsMenuSubDao.save(cmsMenuSub);
            
    }


    /**
     * 修改子菜单 
     * @param updateParam    修改参数
     * @param cmsMenuSubId    子菜单的ID 
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateCmsMenuSub(Long cmsMenuSubId, CmsMenuSubUpdateParamDTO updateParam){
        
        CmsMenuSub cmsMenuSub = findCmsMenuSubById(cmsMenuSubId);
        cmsMenuSub.setName(updateParam.getName());
        cmsMenuSub.setIcon(updateParam.getIcon());
        cmsMenuSub.setSort(updateParam.getSort());
        cmsMenuSub.setMenuId(updateParam.getMenuId());
        cmsMenuSub.setPageId(updateParam.getPageId());
        cmsMenuSub.setRole(updateParam.getRole());
        cmsMenuSub.setHide(updateParam.getHide());
    
        cmsMenuSubDao.save(cmsMenuSub);
            
    }


    /**
     * 删除子菜单 
     * @param cmsMenuSubId    子菜单的ID 
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeCmsMenuSubById(Long cmsMenuSubId){
        
        CmsMenuSub cmsMenuSub = findCmsMenuSubById(cmsMenuSubId);
        cmsMenuSubDao.delete(cmsMenuSub);
            
    }




}