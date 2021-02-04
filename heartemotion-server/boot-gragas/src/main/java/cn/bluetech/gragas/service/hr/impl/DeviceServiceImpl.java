package cn.bluetech.gragas.service.hr.impl;

import java.lang.*;
import java.util.*;
import java.time.LocalDateTime;

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
 * 设备服务实现
 * @author xu
 * @date 2020-12-16 17:05:53
 */ 
@Service
@Slf4j
public class DeviceServiceImpl implements DeviceService{

    /** 设备Dao */
    @Autowired
    private DeviceDao deviceDao;
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
     * 通过ID查询设备 
     * @param deviceId    设备的ID 
     * @return 设备
     */
    @Override
    public Device findDeviceById(Long deviceId){
        
        return deviceDao.findById(deviceId).orElse(null);
            
    }

    @Override
    public String findWearerByDeviceId(String clientId, String deviceId) {
        Device device = queryFactory.select(QDevice.device)
                .from(QDevice.device)
                .where(ExQuery.booleanExpressionBuilder()
                        .and(QDevice.device.clientId.eq(clientId))
                        .and(QDevice.device.deviceId.eq(deviceId))
                        .build())
                .orderBy(QDevice.device.createDate.desc())
                .fetchFirst();

        return device == null ? null : device.getWearer();
    }

    @Override
    public List<Device> listDevice(DevicePageQueryParamDTO queryParam) {
         QueryResults<Device> results = queryFactory.select(QDevice.device)
                .from(QDevice.device)
                .where(ExQuery.booleanExpressionBuilder()
                        .and(ExQuery.eq(QDevice.device.clientId, queryParam.getClientId()))

                        .build())
                .orderBy(QDevice.device.createDate.desc())
                .fetchResults();
         return results.getResults();
    }



    /**
     * 添加设备 
     * @param insertParam    添加参数 
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertDevice(DeviceInsertParamDTO insertParam){
        
        Device device = new Device();
        device.setCreateDate(LocalDateTime.now());
        device.setClientId(insertParam.getClientId());
        device.setDeviceId(insertParam.getDeviceId());
        device.setDeviceName(insertParam.getDeviceName());
        device.setWearer(insertParam.getWearer());
        device.setPolice(insertParam.getPolice());
    
        deviceDao.save(device);
            
    }


    /**
     * 修改设备 
     * @param updateParam    修改参数
     * @param deviceId    设备的ID 
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateDevice(Long deviceId, DeviceUpdateParamDTO updateParam){
        
        Device device = findDeviceById(deviceId);
        if(updateParam.getWearer() != null){
            device.setWearer(updateParam.getWearer());
        }

        if(updateParam.getPolice() != null){
            device.setPolice(updateParam.getPolice());
        }

        if(updateParam.getDeviceName() != null){
            device.setDeviceName(updateParam.getDeviceName());
        }

        deviceDao.save(device);

        updateWearer(device.getDeviceId(), updateParam.getWearer());
    }

    @Override
    public void updateWearer(String deviceId, String wearer) {
        List<Device> results = queryFactory.select(QDevice.device)
                .from(QDevice.device)
                .where(ExQuery.booleanExpressionBuilder()
                        .and(ExQuery.eq(QDevice.device.deviceId, deviceId))
                        .build())
                .fetchResults().getResults();
        for (Device result : results) {
            result.setWearer(wearer);
            deviceDao.save(result);
        }
    }


    /**
     * 删除设备 
     * @param deviceId    设备的ID 
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeDeviceById(Long deviceId){
        
        Device device = findDeviceById(deviceId);
        deviceDao.delete(device);
            
    }




}