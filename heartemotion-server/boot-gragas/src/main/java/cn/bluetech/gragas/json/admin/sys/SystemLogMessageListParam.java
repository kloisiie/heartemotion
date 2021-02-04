package cn.bluetech.gragas.json.admin.sys;

import com.brframework.commonweb.json.BaseJson;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author xu
 * @date 2020/5/14 15:27
 */
@ApiModel
@Data
public class SystemLogMessageListParam extends BaseJson {


    /** 搜索 */
    @ApiModelProperty(value = "搜索")
    private String search;

    @ApiModelProperty(value = "Level")
    private List<String> level;

    @ApiModelProperty(value = "threadName")
    private String threadName;
    @ApiModelProperty(value = "loggerName")
    private String loggerName;

    /** 时间区间 */
    @ApiModelProperty(value = "时间区间")
    private List<LocalDateTime> timeBetween;
}
