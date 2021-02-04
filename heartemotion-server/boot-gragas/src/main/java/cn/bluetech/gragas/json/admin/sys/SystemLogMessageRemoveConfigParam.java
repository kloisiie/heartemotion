package cn.bluetech.gragas.json.admin.sys;

import com.brframework.commonweb.json.BaseJson;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author laolian
 * @date 2020-08-29 14:18:11
 */
@ApiModel
@Data
public class SystemLogMessageRemoveConfigParam extends BaseJson {

    @ApiModelProperty(value = "日志等级")
    private String level;

    @ApiModelProperty(value = "线程名字")
    private String threadName;

    @ApiModelProperty(value = "日志写入名称")
    private String loggerName;

    @ApiModelProperty(value = "清理周期")
    private String period;
}
