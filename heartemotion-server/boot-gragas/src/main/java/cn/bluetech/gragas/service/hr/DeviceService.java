package cn.bluetech.gragas.service.hr;

import java.lang.*;
import java.util.*;
import cn.bluetech.gragas.entity.hr.*;
import org.springframework.data.domain.Page;
import com.brframework.commonweb.json.PageParam;
import cn.bluetech.gragas.pojo.hr.*;


/**
 * 设备服务实现
 * @author xu
 * @date 2020-12-16 17:05:53
 */ 
public interface DeviceService{



    /**
     * 通过ID查询设备 
     * @param deviceId    设备的ID 
     * @return 设备
     */
     Device findDeviceById(Long deviceId);

    /**
     * 通过设备标识查询设备
     * @param clientId  客户端ID
     * @param deviceId  设备标识
     * @return  设备
     */
    String findWearerByDeviceId(String clientId, String deviceId);


    /**
     * 设备列表
     * @param queryParam
     * @return
     */
     List<Device> listDevice(DevicePageQueryParamDTO queryParam);


    /**
     * 添加设备 
     * @param insertParam    添加参数 
     */
     void insertDevice(DeviceInsertParamDTO insertParam);


    /**
     * 修改设备 
     * @param updateParam    修改参数
     * @param deviceId    设备的ID 
     */
     void updateDevice(Long deviceId, DeviceUpdateParamDTO updateParam);

    /**
     * 修改设备标识下的所有佩戴人
     * @param deviceId
     */
     void updateWearer(String deviceId, String wearer);

    /**
     * 删除设备 
     * @param deviceId    设备的ID 
     */
     void removeDeviceById(Long deviceId);




}