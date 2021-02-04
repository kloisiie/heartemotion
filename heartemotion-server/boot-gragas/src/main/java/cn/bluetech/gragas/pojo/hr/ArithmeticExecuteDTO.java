package cn.bluetech.gragas.pojo.hr;

import cn.bluetech.gragas.globals.HeartRateConstant;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * @author xu
 * @date 2020/12/18 10:57
 */
@Data
public class ArithmeticExecuteDTO {
    @ApiModelProperty(value = "执行状态(-1=执行失败,0=数据不足,1=执行成功)", required = true, example = "1")
    private Integer status;
    @ApiModelProperty(value = "标注状态(无情绪、平稳、烦躁、高兴)", required = true, example = "CALMNESS")
    private HeartRateConstant.LabelStatus labelStatus;
    @ApiModelProperty(value = "应对手段", required = true, example = "各种方法")
    private String means;

}
