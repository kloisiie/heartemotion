package cn.bluetech.gragas.service.client;

import java.lang.*;
import java.util.*;
import cn.bluetech.gragas.entity.client.*;
import org.springframework.data.domain.Page;
import com.brframework.commonweb.json.PageParam;
import cn.bluetech.gragas.pojo.client.*;


/**
 * 操作日志服务实现
 * @author xu
 * @date 2020-12-16 17:40:35
 */ 
public interface OptionLogService{

    /**
     * 通过ID查询操作日志 
     * @param optionLogId    操作日志的ID 
     * @return 操作日志
     */
     OptionLog findOptionLogById(Long optionLogId);


    /**
     * 分页查询操作日志 
     * @param queryParam    查询参数
     * @param pageParam    分页参数 
     * @return 操作日志分页
     */
     Page<OptionLog> findOptionLogPage(PageParam pageParam, OptionLogPageQueryParamDTO queryParam);


    /**
     * 添加操作日志 
     * @param insertParam    添加参数 
     */
     void insertOptionLog(OptionLogInsertParamDTO insertParam);


    /**
     * 修改操作日志 
     * @param updateParam    修改参数
     * @param optionLogId    操作日志的ID 
     */
     void updateOptionLog(Long optionLogId, OptionLogUpdateParamDTO updateParam);


    /**
     * 删除操作日志 
     * @param optionLogId    操作日志的ID 
     */
     void removeOptionLogById(Long optionLogId);




}