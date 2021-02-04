package com.brframework.log.pojo;

import lombok.Data;

/**
 * 按搜索条件手动清理数据
 * @author laolian
 * @date 2020-8-27 17:35:13
 */
@Data
public class LogMessageRemoveDTO {

    /** 开始时间 */
    Long startTime;
    /** 结束时间 */
    Long endTime;
    /** 日志级别 */
    String level;
    String threadName;
    String loggerName;

}
