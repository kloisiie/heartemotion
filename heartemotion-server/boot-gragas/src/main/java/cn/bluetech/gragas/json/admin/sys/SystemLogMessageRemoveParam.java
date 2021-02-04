package cn.bluetech.gragas.json.admin.sys;

import com.brframework.commonweb.json.BaseJson;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author xu
 * @date 2020/5/14 15:27
 */
@ApiModel
@Data
public class SystemLogMessageRemoveParam extends BaseJson {

    @ApiModelProperty(value = "日志等级")
    private String level;

    @ApiModelProperty(value = "线程名字")
    private String threadName;

    @ApiModelProperty(value = "日志写入名称")
    private String loggerName;

    @ApiModelProperty(value = "时间区间")
    private String timeBetween;
}
