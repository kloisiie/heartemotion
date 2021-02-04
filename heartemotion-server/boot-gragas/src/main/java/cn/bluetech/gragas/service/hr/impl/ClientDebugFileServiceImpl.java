package cn.bluetech.gragas.service.hr.impl;

import java.lang.*;
import java.time.ZoneId;
import java.util.*;
import java.time.LocalDateTime;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.springframework.data.domain.*;
import com.brframework.commonweb.json.PageParam;
import cn.bluetech.gragas.dao.hr.*;
import cn.bluetech.gragas.entity.hr.*;
import cn.bluetech.gragas.pojo.hr.*;
import cn.bluetech.gragas.service.hr.*;
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
 * 用户调试文件服务实现
 * @author xu
 * @date 2020-12-16 21:47:34
 */ 
@Service
@Slf4j
public class ClientDebugFileServiceImpl implements ClientDebugFileService{

    /** 用户调试文件Dao */
    @Autowired
    private ClientDebugFileDao clientDebugFileDao;
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
     * 通过ID查询用户调试文件 
     * @param clientDebugFileId    用户调试文件的ID 
     * @return 用户调试文件
     */
    @Override
    public ClientDebugFile findClientDebugFileById(Long clientDebugFileId){
        
        return clientDebugFileDao.findById(clientDebugFileId).orElse(null);
            
    }


    /**
     * 分页查询用户调试文件 
     * @param queryParam    查询参数
     * @param pageParam    分页参数 
     * @return 用户调试文件分页
     */
    @Override
    public Page<ClientDebugFile> findClientDebugFilePage(PageParam pageParam, ClientDebugFilePageQueryParamDTO queryParam){
        
        QueryResults<ClientDebugFile> results = queryFactory.select(QClientDebugFile.clientDebugFile)
            .from(QClientDebugFile.clientDebugFile)
            .where(ExQuery.booleanExpressionBuilder()
                .and(QClientDebugFile.clientDebugFile.clientId.eq(queryParam.getClientId()))
                .build())
            .offset(pageParam.getPageIndex() * pageParam.getPageSize())
            .limit(pageParam.getPageSize())
            .orderBy(QClientDebugFile.clientDebugFile.createDate.desc())
            .fetchResults();
    
        return new PageImpl<>(results.getResults(), Pageable.unpaged(), results.getTotal());
            
    }


    /**
     * 添加用户调试文件 
     * @param insertParam    添加参数 
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertClientDebugFile(ClientDebugFileInsertParamDTO insertParam){
        ClientDebugFile clientDebugFile = new ClientDebugFile();
        clientDebugFile.setCreateDate(LocalDateTime.now());
        clientDebugFile.setFileName(insertParam.getFileName());
        clientDebugFile.setContent(insertParam.getContent());
        JSONArray objects = JSON.parseArray(insertParam.getContent());

        clientDebugFile.setStartTime(new Date(objects.getJSONObject(0).getLong("hrTime") * 1000)
                .toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        clientDebugFile.setEndTime(new Date(objects.getJSONObject(objects.size() - 1).getLong("hrTime") * 1000)
                .toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        clientDebugFile.setClientId(insertParam.getClientId());
    
        clientDebugFileDao.save(clientDebugFile);
    }


    /**
     * 修改用户调试文件 
     * @param updateParam    修改参数
     * @param clientDebugFileId    用户调试文件的ID 
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateClientDebugFile(Long clientDebugFileId, ClientDebugFileUpdateParamDTO updateParam){
        
        ClientDebugFile clientDebugFile = findClientDebugFileById(clientDebugFileId);
    
        clientDebugFileDao.save(clientDebugFile);
            
    }


    /**
     * 删除用户调试文件 
     * @param clientDebugFileId    用户调试文件的ID 
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeClientDebugFileById(Long clientDebugFileId){
        
        ClientDebugFile clientDebugFile = findClientDebugFileById(clientDebugFileId);
        clientDebugFileDao.delete(clientDebugFile);
            
    }




}