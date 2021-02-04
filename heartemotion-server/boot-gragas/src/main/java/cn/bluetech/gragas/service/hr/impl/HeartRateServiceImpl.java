package cn.bluetech.gragas.service.hr.impl;

import java.lang.*;
import java.util.*;
import java.time.LocalDateTime;

import cn.bluetech.gragas.globals.HeartRateConstant;
import cn.bluetech.gragas.dao.hr.*;
import cn.bluetech.gragas.entity.hr.*;
import cn.bluetech.gragas.pojo.hr.*;
import cn.bluetech.gragas.service.hr.*;
import com.brframework.commonweb.json.PageParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
 * 心率服务实现
 * @author xu
 * @date 2020-12-16 17:11:27
 */ 
@Service
@Slf4j
public class HeartRateServiceImpl implements HeartRateService{

    /** 心率Dao */
    @Autowired
    private HeartRateDao heartRateDao;
    /** QueryDSL查询 */
    private JPAQueryFactory queryFactory;

    @Autowired
    private ArithmeticService arithmeticService;

    @Autowired
    private PoliceService policeService;

    /**
     * 设置QueryFactory 
     * @param entityManager    entity manager 
     */
    @Resource
    public void setQueryFactory(@Autowired EntityManager entityManager){
        
        queryFactory = new JPAQueryFactory(entityManager);
            
    }


    /**
     * 通过ID查询心率 
     * @param heartRateId    心率的ID 
     * @return 心率
     */
    @Override
    public HeartRate findHeartRateById(Long heartRateId){
        
        return heartRateDao.findById(heartRateId).orElse(null);
            
    }

    @Override
    public HeartRate findNewestByDeviceId(String deviceId) {
        HeartRate heartRate = queryFactory.select(QHeartRate.heartRate)
                .from(QHeartRate.heartRate)
                .where(ExQuery.booleanExpressionBuilder()
                        .and(ExQuery.eq(QHeartRate.heartRate.deviceId, deviceId))
                        .build())
                .orderBy(QHeartRate.heartRate.hrTime.desc())
                .fetchFirst();
        return heartRate;
    }

    @Override
    public List<HeartRate> listHeartRate(String deviceId, HeartRateListQueryParamDTO queryParam) {
        QueryResults<HeartRate> results = queryFactory.select(QHeartRate.heartRate)
                .from(QHeartRate.heartRate)
                .where(ExQuery.booleanExpressionBuilder()
                        .and(ExQuery.eq(QHeartRate.heartRate.deviceId, deviceId))
                        .and(ExQuery.betweenDate(QHeartRate.heartRate.hrTime, queryParam.getHrTimeStart(),
                                queryParam.getHrTimeEnd()))

                        .build())
                .orderBy(QHeartRate.heartRate.hrTime.asc())
                .fetchResults();

        return results.getResults();
    }

    @Override
    public Page<HeartRate> findHeartRatePage(PageParam pageParam, HeartRatePageQueryParamDTO queryParam) {
        QueryResults<HeartRate> results = queryFactory.select(QHeartRate.heartRate)
                .from(QHeartRate.heartRate)
                .where(ExQuery.booleanExpressionBuilder()
                        .and(ExQuery.betweenDate(QHeartRate.heartRate.hrTime, queryParam.getHrTimeStart(),
                                queryParam.getHrTimeEnd()))
                        .and(ExQuery.eq(QHeartRate.heartRate.deviceId, queryParam.getDeviceId()))
                        .and(ExQuery.eq(QHeartRate.heartRate.labelType, queryParam.getLabelType()))
                        .and(ExQuery.eq(QHeartRate.heartRate.labelStatus, queryParam.getLabelStatus()))
                        .and(ExQuery.eq(QHeartRate.heartRate.wearer, queryParam.getWearer()))
                        .build())
                .offset(pageParam.getPageIndex() * pageParam.getPageSize())
                .limit(pageParam.getPageSize())
                .orderBy(QHeartRate.heartRate.hrTime.desc())
                .fetchResults();
        return new PageImpl<>(results.getResults(), Pageable.unpaged(), results.getTotal());
    }

    private HeartRateConstant.LabelStatus previousLabelStatus;

    /**
     * 添加心率 
     * @param insertParam    添加参数 
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertHeartRate(HeartRateInsertParamDTO insertParam){
        
        HeartRate heartRate = new HeartRate();
        heartRate.setCreateDate(LocalDateTime.now());
        heartRate.setHrTime(insertParam.getHrTime());
        heartRate.setDeviceId(insertParam.getDeviceId());
        heartRate.setHrValue(insertParam.getHrValue());
        heartRate.setWearer(insertParam.getWearer());

        ArithmeticExecuteDTO executeDTO = arithmeticService.executeStandard(insertParam.getDeviceId(),
                insertParam.getHrTime(), insertParam.getHrValue());
        heartRate.setLabelType(HeartRateConstant.LabelType.ARITHMETIC);
        if(executeDTO.getStatus() == 0 || executeDTO.getStatus() == -1){
            heartRate.setLabelStatus(HeartRateConstant.LabelStatus.NO_MOOD);
            heartRate.setMeans("无情绪应对");
        } else {
            heartRate.setLabelStatus(executeDTO.getLabelStatus());
            heartRate.setMeans(executeDTO.getMeans());
        }

        if(executeDTO.getLabelStatus() != null &&
                executeDTO.getLabelStatus().equals(HeartRateConstant.LabelStatus.AGITATED)){
            //暴躁的情况下需要进行报警，但是要考虑同一段情绪不要重复报警
            if(previousLabelStatus == null || !previousLabelStatus.equals(HeartRateConstant.LabelStatus.AGITATED)){
                PoliceInsertParamDTO policeInsertParam = new PoliceInsertParamDTO();
                policeInsertParam.setPoliceTime(insertParam.getHrTime());
                policeInsertParam.setDeviceId(insertParam.getDeviceId());

                policeService.insertPolice(policeInsertParam);
            }
        }

        previousLabelStatus = executeDTO.getLabelStatus();

        heartRateDao.save(heartRate);
            
    }

    @Override
    public boolean checkStatus(String deviceId, LocalDateTime start, LocalDateTime end) {
        long count = queryFactory.select(QHeartRate.heartRate)
                .from(QHeartRate.heartRate)
                .where(ExQuery.booleanExpressionBuilder()
                        .and(ExQuery.betweenDate(QHeartRate.heartRate.hrTime, start, end))
                        .and(QHeartRate.heartRate.labelStatus.isNotNull())
                        .build())
                .orderBy(QHeartRate.heartRate.hrTime.desc())
                .fetchCount();


        return count > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateLabelStatus(String deviceId, LocalDateTime start,
                                  LocalDateTime end, HeartRateConstant.LabelStatus labelStatus,
                                  String means) {
        long execute = queryFactory.update(QHeartRate.heartRate)
                .set(QHeartRate.heartRate.labelStatus, labelStatus)
                .set(QHeartRate.heartRate.means, means)
                .set(QHeartRate.heartRate.labelType, HeartRateConstant.LabelType.MANUAL)
                .where(ExQuery.booleanExpressionBuilder()
                        .and(QHeartRate.heartRate.deviceId.eq(deviceId))
                        .and(ExQuery.betweenDate(QHeartRate.heartRate.hrTime, start, end))
                        .build())
                .execute();
    }


    /**
     * 修改心率 
     * @param heartRateId    心率的ID
     * @param updateParam    修改参数 
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateHeartRate(Long heartRateId, HeartRateUpdateParamDTO updateParam){
        
        HeartRate heartRate = findHeartRateById(heartRateId);
    
        heartRateDao.save(heartRate);
            
    }


    /**
     * 删除心率 
     * @param heartRateId    心率的ID 
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeHeartRateById(Long heartRateId){
        
        HeartRate heartRate = findHeartRateById(heartRateId);
        heartRateDao.delete(heartRate);
            
    }




}