package com.brframework.log.entity;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author xu
 * @date 2020/5/28 16:55
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "log_message", indexes = {
        @Index(name = "TIME_INDEX", columnList = "timeStamp")
})
@Builder
@ApiModel
public class LogMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ApiModelProperty(value = "timeStamp")
    private Long timeStamp;
    @ApiModelProperty(value = "threadName")
    private String threadName;
    @ApiModelProperty(value = "loggerName")
    private String loggerName;
    @ApiModelProperty(value = "level")
    private String level;
    @ApiModelProperty(value = "ip")
    private String ip;
    @ApiModelProperty(value = "message")
    @Column(length = Integer.MAX_VALUE)
    private String message;

}
