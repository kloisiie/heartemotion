package com.brframework.log.service;

import com.brframework.commonweb.json.PageParam;
import com.brframework.log.appender.DBAppender;
import com.brframework.log.entity.LogMessage;
import com.brframework.log.pojo.LogMessageQueryDTO;
import com.brframework.log.pojo.LogMessageRemoveConfigDTO;
import com.brframework.log.pojo.LogMessageRemoveDTO;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author xu
 * @date 2020/5/28 17:02
 */
public interface LogMessageService {

    /**
     * 分页查询日志
     * @param pageParam       分页参数
     * @param queryParam      查询参数
     * @return  日志列表
     */
    Page<LogMessage> findLogPage(PageParam pageParam, LogMessageQueryDTO queryParam);

    /**
     * 通过ID查询
     * @param id
     * @return
     */
    LogMessage findLogById(Long id);

    /**
     * 写入日志
     * @param logInfos
     */
    void writeLogs(List<DBAppender.LogInfo> logInfos);

    /**
     * 按条件删除数据
     * @param removeDTO
     */
    void removeConditionally(LogMessageRemoveDTO removeDTO);

    /**
     * 根据配置清理数据
     * @param removeDTO
     */
    void removeByConfig(LogMessageRemoveConfigDTO removeDTO);

}
