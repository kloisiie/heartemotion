package com.brframework.log.pojo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author laolian
 * @date 2020-08-29 13:57:08
 */
@ApiModel
@Data
public class LogMessageRemoveConfigDTO {

    /** 每天 */
    public final static String LOG_MESSAGE_REMOVE_CONFIG_PERIOD_DAY = "DAY";
    /** 每周 */
    public final static String LOG_MESSAGE_REMOVE_CONFIG_PERIOD_WEEK = "WEEK";
    /** 每月 */
    public final static String LOG_MESSAGE_REMOVE_CONFIG_PERIOD_MONTH = "MONTH";
    /** 每年 */
    public final static String LOG_MESSAGE_REMOVE_CONFIG_PERIOD_YEAR = "YEAR";

    public final static String LOG_MESSAGE_REMOVE_CONFIG_KEY = "LOG_MESSAGE_REMOVE_CONFIG_KEY";

    /** 日志等级 */
    private String level;

    /** 线程名字 */
    private String threadName;

    /** 日志写入名称 */
    private String loggerName;

    /** 清理周期 */
    private String period;
}
