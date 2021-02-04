package cn.bluetech.gragas.service.hr.impl;

import java.lang.*;
import java.time.ZoneId;
import java.util.*;
import java.time.LocalDateTime;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.brframework.commonweb.exception.HandleException;
import org.springframework.data.domain.*;
import com.brframework.commonweb.json.PageParam;
import cn.bluetech.gragas.dao.hr.*;
import cn.bluetech.gragas.entity.hr.*;
import cn.bluetech.gragas.pojo.hr.*;
import cn.bluetech.gragas.service.hr.*;
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
 * 平台调试文件服务实现
 * @author xu
 * @date 2020-12-16 21:51:27
 */ 
@Service
@Slf4j
public class PlatformDebugFileServiceImpl implements PlatformDebugFileService{

    /** 平台调试文件Dao */
    @Autowired
    private PlatformDebugFileDao platformDebugFileDao;
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
     * 通过ID查询平台调试文件 
     * @param platformDebugFileId    平台调试文件的ID 
     * @return 平台调试文件
     */
    @Override
    public PlatformDebugFile findPlatformDebugFileById(Long platformDebugFileId){
        
        return platformDebugFileDao.findById(platformDebugFileId).orElse(null);
            
    }


    /**
     * 分页查询平台调试文件 
     * @param queryParam    查询参数
     * @param pageParam    分页参数 
     * @return 平台调试文件分页
     */
    @Override
    public Page<PlatformDebugFile> findPlatformDebugFilePage(PageParam pageParam, PlatformDebugFilePageQueryParamDTO queryParam){
        
        QueryResults<PlatformDebugFile> results = queryFactory.select(QPlatformDebugFile.platformDebugFile)
            .from(QPlatformDebugFile.platformDebugFile)
            .where(ExQuery.booleanExpressionBuilder()
                .and(ExQuery.like(QPlatformDebugFile.platformDebugFile.fileName, queryParam.getFileName()))
    
                .build())
            .offset(pageParam.getPageIndex() * pageParam.getPageSize())
            .limit(pageParam.getPageSize())
            .orderBy(QPlatformDebugFile.platformDebugFile.createDate.desc())
            .fetchResults();
    
        return new PageImpl<>(results.getResults(), Pageable.unpaged(), results.getTotal());
            
    }


    /**
     * 添加平台调试文件 
     * @param insertParam    添加参数 
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertPlatformDebugFile(PlatformDebugFileInsertParamDTO insertParam){
        
        PlatformDebugFile platformDebugFile = new PlatformDebugFile();
        platformDebugFile.setCreateDate(LocalDateTime.now());
        platformDebugFile.setFileName(insertParam.getFileName());
        platformDebugFile.setDetails(insertParam.getDetails());
        platformDebugFile.setContent(insertParam.getContent());

        try {
            JSONArray objects = JSON.parseArray(insertParam.getContent());
            platformDebugFile.setStartTime(new Date(objects.getJSONObject(0).getLong("hrTime") * 1000)
                    .toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
            platformDebugFile.setEndTime(new Date(objects.getJSONObject(objects.size() - 1).getLong("hrTime") * 1000)
                    .toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        } catch (JSONException e){
            throw new HandleException("文件格式不符合要求");
        }


    
        platformDebugFileDao.save(platformDebugFile);
            
    }


    /**
     * 修改平台调试文件 
     * @param updateParam    修改参数
     * @param platformDebugFileId    平台调试文件的ID 
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePlatformDebugFile(Long platformDebugFileId, PlatformDebugFileUpdateParamDTO updateParam){
        
        PlatformDebugFile platformDebugFile = findPlatformDebugFileById(platformDebugFileId);
        platformDebugFile.setFileName(updateParam.getFileName());
        platformDebugFile.setDetails(updateParam.getDetails());
        platformDebugFile.setContent(updateParam.getContent());

        try {
            JSONArray objects = JSON.parseArray(updateParam.getContent());
            platformDebugFile.setStartTime(new Date(objects.getJSONObject(0).getLong("hrTime") * 1000)
                    .toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
            platformDebugFile.setEndTime(new Date(objects.getJSONObject(objects.size() - 1).getLong("hrTime") * 1000)
                    .toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        } catch (JSONException e){
            throw new HandleException("文件格式不符合要求");
        }



        platformDebugFileDao.save(platformDebugFile);
            
    }


    /**
     * 删除平台调试文件 
     * @param platformDebugFileId    平台调试文件的ID 
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removePlatformDebugFileById(Long platformDebugFileId){
        
        PlatformDebugFile platformDebugFile = findPlatformDebugFileById(platformDebugFileId);
        platformDebugFileDao.delete(platformDebugFile);
            
    }




}