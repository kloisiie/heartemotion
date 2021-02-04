package cn.bluetech.gragas.service.client.impl;

import java.lang.*;
import java.time.LocalDateTime;

import org.springframework.data.domain.*;
import com.brframework.commonweb.json.PageParam;
import cn.bluetech.gragas.dao.client.*;
import cn.bluetech.gragas.entity.client.*;
import cn.bluetech.gragas.pojo.client.*;
import cn.bluetech.gragas.service.client.*;
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
 * 操作日志服务实现
 * @author xu
 * @date 2020-12-16 17:40:35
 */ 
@Service
@Slf4j
public class OptionLogServiceImpl implements OptionLogService{

    /** 操作日志Dao */
    @Autowired
    private OptionLogDao optionLogDao;
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
     * 通过ID查询操作日志 
     * @param optionLogId    操作日志的ID 
     * @return 操作日志
     */
    @Override
    public OptionLog findOptionLogById(Long optionLogId){
        
        return optionLogDao.findById(optionLogId).orElse(null);
            
    }


    /**
     * 分页查询操作日志 
     * @param queryParam    查询参数
     * @param pageParam    分页参数 
     * @return 操作日志分页
     */
    @Override
    public Page<OptionLog> findOptionLogPage(PageParam pageParam, OptionLogPageQueryParamDTO queryParam){
        
        QueryResults<OptionLog> results = queryFactory.select(QOptionLog.optionLog)
            .from(QOptionLog.optionLog)
            .where(ExQuery.booleanExpressionBuilder()
                .and(ExQuery.betweenDate(QOptionLog.optionLog.createDate, queryParam.getCreateDateStart(), queryParam.getCreateDateEnd()))
                .and(ExQuery.eq(QOptionLog.optionLog.clientId, queryParam.getClientId()))
                .and(ExQuery.eq(QOptionLog.optionLog.callApi, queryParam.getCallApi()))
                .and(ExQuery.eq(QOptionLog.optionLog.ip, queryParam.getIp()))
    
                .build())
            .offset(pageParam.getPageIndex() * pageParam.getPageSize())
            .limit(pageParam.getPageSize())
            .orderBy(QOptionLog.optionLog.createDate.desc())
            .fetchResults();
    
        return new PageImpl<>(results.getResults(), Pageable.unpaged(), results.getTotal());
            
    }


    /**
     * 添加操作日志 
     * @param insertParam    添加参数 
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertOptionLog(OptionLogInsertParamDTO insertParam){
        
        OptionLog optionLog = new OptionLog();
        optionLog.setCreateDate(LocalDateTime.now());
        optionLog.setClientId(insertParam.getClientId());
        optionLog.setCallApi(insertParam.getCallApi());
        optionLog.setIp(insertParam.getIp());
    
        optionLogDao.save(optionLog);
            
    }


    /**
     * 修改操作日志 
     * @param updateParam    修改参数
     * @param optionLogId    操作日志的ID 
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateOptionLog(Long optionLogId, OptionLogUpdateParamDTO updateParam){
        
        OptionLog optionLog = findOptionLogById(optionLogId);
    
        optionLogDao.save(optionLog);
            
    }


    /**
     * 删除操作日志 
     * @param optionLogId    操作日志的ID 
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeOptionLogById(Long optionLogId){
        
        OptionLog optionLog = findOptionLogById(optionLogId);
        optionLogDao.delete(optionLog);
            
    }




}