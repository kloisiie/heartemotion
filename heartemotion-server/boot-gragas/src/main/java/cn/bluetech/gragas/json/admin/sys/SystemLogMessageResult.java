package cn.bluetech.gragas.json.admin.sys;

import com.alibaba.fastjson.annotation.JSONField;
import com.brframework.commonweb.json.BaseJson;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author xu
 * @date 2020/5/28 21:35
 */
@ApiModel
@Data
public class SystemLogMessageResult extends BaseJson {

    private Long id;
    @ApiModelProperty(value = "timeStamp")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime timeStampTime;
    @ApiModelProperty(value = "threadName")
    private String threadName;
    @ApiModelProperty(value = "loggerName")
    private String loggerName;
    @ApiModelProperty(value = "level")
    private String level;
    @ApiModelProperty(value = "ip")
    private String ip;
    @ApiModelProperty(value = "synopsis")
    private String synopsis;
    @ApiModelProperty(value = "message")
    private String message;
}
