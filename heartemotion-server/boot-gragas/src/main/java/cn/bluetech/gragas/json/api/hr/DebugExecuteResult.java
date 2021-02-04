package cn.bluetech.gragas.json.api.hr;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author xu
 * @date 2020/12/16 22:07
 */
@ApiModel
@Data
public class DebugExecuteResult {

    @ApiModelProperty(value = "任务ID", required = true, example = "23123")
    private String taskId;

}
