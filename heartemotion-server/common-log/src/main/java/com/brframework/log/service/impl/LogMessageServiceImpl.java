package com.brframework.log.service.impl;

import com.brframework.commondb.core.ExQuery;
import com.brframework.commondb.utils.QueryUtils;
import com.brframework.commonweb.json.PageParam;
import com.brframework.log.appender.DBAppender;
import com.brframework.log.dao.LogMessageDao;
import com.brframework.log.entity.LogMessage;
import com.brframework.log.entity.QLogMessage;
import com.brframework.log.pojo.LogMessageQueryDTO;
import com.brframework.log.pojo.LogMessageRemoveConfigDTO;
import com.brframework.log.pojo.LogMessageRemoveDTO;
import com.brframework.log.service.LogMessageService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberTemplate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 日志消息服务
 * @author xu
 * @date 2020/5/28 17:03
 */
@Service
@Slf4j
public class LogMessageServiceImpl implements LogMessageService {

    @Autowired
    LogMessageDao logMessageDao;
    @Autowired
    EntityManager entityManager;

    @Override
    public Page<LogMessage> findLogPage(PageParam pageParam, LogMessageQueryDTO queryParam) {

        NumberTemplate<BigDecimal> messageMatch = null;
        if(StringUtils.isNotEmpty(queryParam.getSearch())){
            messageMatch = Expressions.numberTemplate(BigDecimal.class,
                    "match_query({0}, {1})", QLogMessage.logMessage.message, queryParam.getSearch());
        }

        return logMessageDao.findAll(ExQuery.booleanExpressionBuilder()
                        .and(ExQuery.between(QLogMessage.logMessage.timeStamp, queryParam.getStartTime()
                                , queryParam.getEndTime()))
                        .and(ExQuery.eq(QLogMessage.logMessage.level, queryParam.getLevel()))
                        .and(ExQuery.eq(QLogMessage.logMessage.loggerName, queryParam.getLoggerName()))
                        .and(ExQuery.eq(QLogMessage.logMessage.threadName, queryParam.getThreadName()))
                        .and(messageMatch != null ? messageMatch.goe(2) : null)
                        .build()
                , QueryUtils.toPageRequest(pageParam, Sort.by(Sort.Direction.DESC, "timeStamp")));
    }

    @Override
    public LogMessage findLogById(Long id) {
        return logMessageDao.findById(id).orElse(null);
    }


    @Override
    @Transactional
    public void writeLogs(List<DBAppender.LogInfo> logInfos) {
        List<String> insertSqls = Lists.newArrayList();
        Map<String, Object> params = Maps.newHashMap();

        for (int i = 0; i < logInfos.size(); i++) {
            DBAppender.LogInfo logInfo = logInfos.get(i);
            int cursor = 0;
            StringBuilder sb = new StringBuilder();
            String timeStampKey = i + "_" + ++cursor;
            String threadNameKey = i + "_" + ++cursor;
            String loggerNameKey = i + "_" + ++cursor;
            String levelKey = i + "_" + ++cursor;
            String messageKey = i + "_" + ++cursor;
            String ipKey = i + "_" + ++cursor;

            params.put(timeStampKey, logInfo.getTimeStamp());
            params.put(threadNameKey, logInfo.getThreadName());
            params.put(loggerNameKey, logInfo.getLoggerName());
            params.put(levelKey, logInfo.getLevel());
            params.put(messageKey, logInfo.getMessage());
            params.put(ipKey, logInfo.getIp());

            sb.append("(");
            sb.append(":").append(timeStampKey).append(",");
            sb.append(":").append(threadNameKey).append(",");
            sb.append(":").append(loggerNameKey).append(",");
            sb.append(":").append(levelKey).append(",");
            sb.append(":").append(messageKey).append(",");
            sb.append(":").append(ipKey);
            sb.append(")");
            insertSqls.add(sb.toString());
        }


        String sb = "insert into log_message (" +
                "time_stamp, thread_name, logger_name, level, message, ip" +
                ") values " +
                StringUtils.join(insertSqls, ",");
        Query nativeQuery = entityManager.createNativeQuery(sb);
        for (Map.Entry<String, Object> param : params.entrySet()) {
            nativeQuery.setParameter(param.getKey(), param.getValue());
        }
        nativeQuery.executeUpdate();
    }

    @Override
    @Transactional
    public void removeConditionally(LogMessageRemoveDTO removeDTO) {
        StringBuilder sb = new StringBuilder("delete from log_message where 1 = 1 ");
        if (StringUtils.isNotEmpty(removeDTO.getLevel())) {
            sb.append(" and level = '" + removeDTO.getLevel() + "'");
        }
        if (null != removeDTO.getStartTime()) {
            sb.append(" and time_stamp > " + removeDTO.getStartTime());
        }
        if (null != removeDTO.getEndTime()) {
            sb.append(" and time_stamp < " + removeDTO.getEndTime());
        }
        if (StringUtils.isNotEmpty(removeDTO.getThreadName())) {
            sb.append(" and thread_name = '" + removeDTO.getThreadName() + "'");
        }
        if (StringUtils.isNotEmpty(removeDTO.getLoggerName())) {
            sb.append(" and logger_name = '" + removeDTO.getLoggerName() + "'");
        }
        Query nativeQuery = entityManager.createNativeQuery(sb.toString());
        nativeQuery.executeUpdate();
    }

    @Override
    @Transactional
    public void removeByConfig(LogMessageRemoveConfigDTO removeDTO) {
        if (StringUtils.isNotEmpty(removeDTO.getPeriod())) {
            StringBuilder sb = new StringBuilder("delete from log_message where 1 = 1 ");
            if (StringUtils.isNotEmpty(removeDTO.getLevel())) {
                sb.append(" and level = '" + removeDTO.getLevel() + "'");
            }
            if (StringUtils.isNotEmpty(removeDTO.getThreadName())) {
                sb.append(" and thread_name = '" + removeDTO.getThreadName() + "'");
            }
            if (StringUtils.isNotEmpty(removeDTO.getLoggerName())) {
                sb.append(" and logger_name = '" + removeDTO.getLoggerName() + "'");
            }

            Query nativeQuery;
            LocalDateTime now = LocalDateTime.now();
            switch (removeDTO.getPeriod()) {
                case LogMessageRemoveConfigDTO.LOG_MESSAGE_REMOVE_CONFIG_PERIOD_DAY:
                    nativeQuery = entityManager.createNativeQuery(sb.toString());
                    nativeQuery.executeUpdate();
                    break;

                case LogMessageRemoveConfigDTO.LOG_MESSAGE_REMOVE_CONFIG_PERIOD_WEEK:
                    //每周一清理数据
                    if (DayOfWeek.MONDAY.equals(now.getDayOfWeek())) {
                        nativeQuery = entityManager.createNativeQuery(sb.toString());
                        nativeQuery.executeUpdate();
                    }
                    break;

                case LogMessageRemoveConfigDTO.LOG_MESSAGE_REMOVE_CONFIG_PERIOD_MONTH:
                    //每月1号清理数据
                    if (1 == now.getDayOfMonth()) {
                        nativeQuery = entityManager.createNativeQuery(sb.toString());
                        nativeQuery.executeUpdate();
                    }
                    break;

                case LogMessageRemoveConfigDTO.LOG_MESSAGE_REMOVE_CONFIG_PERIOD_YEAR:
                    //每年的第一天清理数据
                    if (1 == now.getDayOfYear()) {
                        nativeQuery = entityManager.createNativeQuery(sb.toString());
                        nativeQuery.executeUpdate();
                    }
                    break;

                default:
                    break;
            }
        }
    }
}
