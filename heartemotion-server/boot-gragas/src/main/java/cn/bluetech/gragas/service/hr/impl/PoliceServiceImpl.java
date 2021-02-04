package cn.bluetech.gragas.service.hr.impl;

import java.lang.*;
import java.util.*;
import java.time.LocalDateTime;
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
 * 报警记录服务实现
 * @author xu
 * @date 2020-12-16 17:23:08
 */ 
@Service
@Slf4j
public class PoliceServiceImpl implements PoliceService{

    /** 报警记录Dao */
    @Autowired
    private PoliceDao policeDao;
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
     * 通过ID查询报警记录 
     * @param policeId    报警记录的ID 
     * @return 报警记录
     */
    @Override
    public Police findPoliceById(Long policeId){
        
        return policeDao.findById(policeId).orElse(null);
            
    }


    /**
     * 分页查询报警记录 
     * @param queryParam    查询参数
     * @param pageParam    分页参数 
     * @return 报警记录分页
     */
    @Override
    public Page<Police> findPolicePage(PageParam pageParam, PolicePageQueryParamDTO queryParam){
        
        QueryResults<Police> results = queryFactory.select(QPolice.police)
            .from(QPolice.police)
            .where(ExQuery.booleanExpressionBuilder()
                .and(ExQuery.eq(QPolice.police.deviceId, queryParam.getDeviceId()))
    
                .build())
            .offset(pageParam.getPageIndex() * pageParam.getPageSize())
            .limit(pageParam.getPageSize())
            .orderBy(QPolice.police.createDate.desc())
            .fetchResults();
    
        return new PageImpl<>(results.getResults(), Pageable.unpaged(), results.getTotal());
            
    }

    @Override
    public List<Police> listPoliceByPoll(String[] deviceIds, LocalDateTime start, LocalDateTime end) {
        List<Police> results = queryFactory.select(QPolice.police)
                .from(QPolice.police)
                .where(ExQuery.booleanExpressionBuilder()
                        .and(ExQuery.in(QPolice.police.deviceId, deviceIds))
                        .and(ExQuery.betweenDate(QPolice.police.policeTime, start, end))
                        .build())
                .orderBy(QPolice.police.createDate.desc())
                .fetchResults().getResults();
        return results;
    }

    @Override
    public Long countPoliceByDeviceId(String deviceId) {
        long count = queryFactory.select(QPolice.police.count())
                .from(QPolice.police)
                .where(ExQuery.booleanExpressionBuilder()
                        .and(ExQuery.eq(QPolice.police.deviceId, deviceId))
                        .build())
                .fetchCount();
        return count;
    }


    /**
     * 添加报警记录 
     * @param insertParam    添加参数 
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertPolice(PoliceInsertParamDTO insertParam){
        
        Police police = new Police();
        police.setCreateDate(LocalDateTime.now());
        police.setPoliceTime(insertParam.getPoliceTime());
        police.setDeviceId(insertParam.getDeviceId());
    
        policeDao.save(police);
            
    }


    /**
     * 修改报警记录 
     * @param policeId    报警记录的ID
     * @param updateParam    修改参数 
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePolice(Long policeId, PoliceUpdateParamDTO updateParam){
        
        Police police = findPoliceById(policeId);
    
        policeDao.save(police);
            
    }


    /**
     * 删除报警记录 
     * @param policeId    报警记录的ID 
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removePoliceById(Long policeId){
        
        Police police = findPoliceById(policeId);
        policeDao.delete(police);
            
    }




}