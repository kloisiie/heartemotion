package cn.bluetech.gragas.json.api.hr;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author xu
 * @date 2020/12/16 22:04
 */
@ApiModel
@Data
public class DebugExecuteParam {

    @ApiModelProperty(value = "算法ID", required = true, example = "23123")
    @NotNull(message = "算法ID不能为空")
    private Long arithmeticId;


    @ApiModelProperty(value = "调试文件id", required = true)
    @NotNull(message = "调试文件id不能为空")
    private List<Long> clientDebugFileIds;
}
