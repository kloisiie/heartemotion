package cn.bluetech.gragas.service.hr;

import java.lang.*;
import java.time.LocalDateTime;
import java.util.*;
import cn.bluetech.gragas.entity.hr.*;
import cn.bluetech.gragas.globals.HeartRateConstant;
import org.springframework.data.domain.Page;
import com.brframework.commonweb.json.PageParam;
import cn.bluetech.gragas.pojo.hr.*;


/**
 * 心率服务实现
 * @author xu
 * @date 2020-12-16 17:11:26
 */ 
public interface HeartRateService{



    /**
     * 通过ID查询心率 
     * @param heartRateId    心率的ID 
     * @return 心率
     */
     HeartRate findHeartRateById(Long heartRateId);

    /**
     * 查询设备最新的心率数据
     * @param deviceId    设备ID
     * @return  心率数据
     */
    HeartRate findNewestByDeviceId(String deviceId);


    /**
     * 心率列表
     * @param deviceId
     * @param queryParam
     * @return
     */
     List<HeartRate> listHeartRate(String deviceId, HeartRateListQueryParamDTO queryParam);

    /**
     * 分页查询
     * @param pageParam
     * @param queryParam
     * @return
     */
     Page<HeartRate> findHeartRatePage(PageParam pageParam, HeartRatePageQueryParamDTO queryParam);


    /**
     * 添加心率 
     * @param insertParam    添加参数 
     */
     void insertHeartRate(HeartRateInsertParamDTO insertParam);

    /**
     * 检查某个时间段内的心率数据是否已有标注
     * @param deviceId   设备ID
     * @param start      开始时间
     * @param end        结束时间
     * @return  是否已有标注
     */
     boolean checkStatus(String deviceId, LocalDateTime start, LocalDateTime end);


    /**
     * 更新标注状态
     * @param deviceId      设备ID
     * @param start         开始时间
     * @param end           结束时间
     * @param labelStatus   标注状态
     * @param means         应对方法
     */
     void updateLabelStatus(String deviceId, LocalDateTime start, LocalDateTime end,
                            HeartRateConstant.LabelStatus labelStatus, String means);

    /**
     * 修改心率 
     * @param heartRateId    心率的ID
     * @param updateParam    修改参数 
     */
     void updateHeartRate(Long heartRateId, HeartRateUpdateParamDTO updateParam);


    /**
     * 删除心率 
     * @param heartRateId    心率的ID 
     */
     void removeHeartRateById(Long heartRateId);




}