package cn.bluetech.gragas.service.hr;

import java.lang.*;
import java.time.LocalDateTime;
import java.util.*;
import cn.bluetech.gragas.entity.hr.*;
import org.springframework.data.domain.Page;
import com.brframework.commonweb.json.PageParam;
import cn.bluetech.gragas.pojo.hr.*;


/**
 * 报警记录服务实现
 * @author xu
 * @date 2020-12-16 17:23:08
 */ 
public interface PoliceService{



    /**
     * 通过ID查询报警记录 
     * @param policeId    报警记录的ID 
     * @return 报警记录
     */
     Police findPoliceById(Long policeId);


    /**
     * 分页查询报警记录 
     * @param queryParam    查询参数
     * @param pageParam    分页参数 
     * @return 报警记录分页
     */
     Page<Police> findPolicePage(PageParam pageParam, PolicePageQueryParamDTO queryParam);

    /**
     * 轮询设备报警信息
     * @param deviceIds  设备列表
     * @param start      开始时间
     * @param end        结束时间
     * @return
     */
     List<Police> listPoliceByPoll(String[] deviceIds, LocalDateTime start, LocalDateTime end);

    /**
     * 查询设备的报警数量
     * @param deviceId
     * @return
     */
     Long countPoliceByDeviceId(String deviceId);

    /**
     * 添加报警记录 
     * @param insertParam    添加参数 
     */
     void insertPolice(PoliceInsertParamDTO insertParam);


    /**
     * 修改报警记录 
     * @param policeId    报警记录的ID
     * @param updateParam    修改参数 
     */
     void updatePolice(Long policeId, PoliceUpdateParamDTO updateParam);


    /**
     * 删除报警记录 
     * @param policeId    报警记录的ID 
     */
     void removePoliceById(Long policeId);




}