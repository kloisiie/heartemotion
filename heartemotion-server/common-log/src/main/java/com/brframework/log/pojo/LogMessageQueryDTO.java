package com.brframework.log.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author xu
 * @date 2020/5/28 18:50
 */
@Data
public class LogMessageQueryDTO {

    /** 提交开始时间 */
    Long startTime;
    /** 提交结束时间 */
    Long endTime;
    /** 日志级别 */
    String level;
    /** 搜索 */
    String search;
    String threadName;
    String loggerName;

}
