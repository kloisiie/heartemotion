package cn.bluetech.gragas.json.api.hr;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author xu
 * @date 2020/12/16 22:10
 */
@ApiModel
@Data
public class DebugExecuteArithmeticResult {

    @ApiModelProperty(value = "状态(1=正在执行，2=执行成功，3=执行失败)", required = true, example = "1")
    private Integer status;
    @ApiModelProperty(value = "总准确率", required = false, example = "100")
    private Double sumRate;
    @ApiModelProperty(value = "无情绪准确率", required = false, example = "100")
    private Double noMoodRate;
    @ApiModelProperty(value = "平稳准确率", required = false, example = "100")
    private Double calmnessRate;
    @ApiModelProperty(value = "烦躁准确率", required = false, example = "100")
    private Double agitatedRate;
    @ApiModelProperty(value = "高兴准确率", required = false, example = "100")
    private Double happyRate;
    @ApiModelProperty(value = "结果对比细节", required = false)
    private List<DebugExecuteArithmeticEntryResult> enters;
}
