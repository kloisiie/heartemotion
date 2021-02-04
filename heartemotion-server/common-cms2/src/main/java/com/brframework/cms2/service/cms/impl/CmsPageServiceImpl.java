package com.brframework.cms2.service.cms.impl;

import java.lang.*;
import java.util.*;
import java.time.LocalDateTime;

import com.brframework.commonweb.exception.HandleException;
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
 * 页面服务实现
 * @author xu
 * @date 2020-11-07 17:52:26
 */ 
@Service
@Slf4j
public class CmsPageServiceImpl implements CmsPageService{

    /** 页面Dao */
    @Autowired
    private CmsPageDao cmsPageDao;
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
     * 通过ID查询页面 
     * @param cmsPageId    页面的ID 
     * @return 页面
     */
    @Override
    public CmsPage findCmsPageById(Long cmsPageId){
        
        return cmsPageDao.findById(cmsPageId).orElse(null);
            
    }

    @Override
    public CmsPage findCmsPageByRouteName(String routeName) {
        return cmsPageDao.findOne(ExQuery.eq(QCmsPage.cmsPage.routeName, routeName)).orElse(null);
    }

    @Override
    public List<CmsPage> listCmsPageByGroup(Long groupId) {
        return (List<CmsPage>) cmsPageDao.findAll(
                ExQuery.eq(QCmsPage.cmsPage.groupId, groupId),
                QCmsPage.cmsPage.sort.asc()
        );
    }


    /**
     * 添加页面 
     * @param insertParam    添加参数 
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertCmsPage(CmsPageInsertParamDTO insertParam){
        
        CmsPage cmsPage = new CmsPage();
        cmsPage.setCreateDate(LocalDateTime.now());
        cmsPage.setName(insertParam.getName());
        cmsPage.setType(insertParam.getType());
        cmsPage.setGroupId(insertParam.getGroupId());
        cmsPage.setRoute(insertParam.getRoute());
        cmsPage.setSort(insertParam.getSort());
        cmsPage.setRouteName(
                insertParam.getRoute().replace("route://", "").split(" ")[0]
        );

        if(findCmsPageByRouteName(cmsPage.getRouteName()) != null){
            throw new HandleException("路由名称不允许重复");
        }
    
        cmsPageDao.save(cmsPage);
            
    }


    /**
     * 修改页面 
     * @param updateParam    修改参数
     * @param cmsPageId    页面的ID 
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateCmsPage(Long cmsPageId, CmsPageUpdateParamDTO updateParam){
        
        CmsPage cmsPage = findCmsPageById(cmsPageId);
        cmsPage.setName(updateParam.getName());
        cmsPage.setType(updateParam.getType());
        cmsPage.setGroupId(updateParam.getGroupId());
        cmsPage.setRoute(updateParam.getRoute());
        cmsPage.setSort(updateParam.getSort());
        cmsPage.setRouteName(
                updateParam.getRoute().replace("route://", "").split(" ")[0]
        );

        CmsPage cmsPageByRouteName = findCmsPageByRouteName(cmsPage.getRouteName());
        if(cmsPageByRouteName != null && !cmsPageByRouteName.getId().equals(cmsPage.getId())){
            throw new HandleException("路由名称不允许重复");
        }
    
        cmsPageDao.save(cmsPage);
            
    }

    @Override
    public void updateContent(Long cmsPageId, String content) {
        CmsPage cmsPage = findCmsPageById(cmsPageId);
        cmsPage.setContent(content);

        cmsPageDao.save(cmsPage);
    }


    /**
     * 删除页面 
     * @param cmsPageId    页面的ID 
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeCmsPageById(Long cmsPageId){
        
        CmsPage cmsPage = findCmsPageById(cmsPageId);
        cmsPageDao.delete(cmsPage);
            
    }




}