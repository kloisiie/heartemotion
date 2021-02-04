package com.brframework.log.dao;

import com.brframework.commondb.core.CommonRepository;
import com.brframework.log.entity.LogMessage;

/**
 * 日志消息
 * @author xu
 * @date 2020/5/28 17:01
 */
public interface LogMessageDao extends CommonRepository<Long, LogMessage> {
}
