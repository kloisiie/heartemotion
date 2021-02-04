package cn.bluetech.gragas.json.api.hr;

import cn.bluetech.gragas.globals.HeartRateConstant;
import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author xu
 * @date 2020/12/16 22:17
 */
@ApiModel
@Data
public class DebugExecuteArithmeticEntryResult {

    @ApiModelProperty(value = "心率时间", required = true, example = "2020-12-16 22:18:54")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime hrTime;
    @ApiModelProperty(value = "心率值", required = true, example = "11")
    private int hrValue;
    @ApiModelProperty(value = "基准算法执行结果", required = true, example = "NO_MOOD")
    private HeartRateConstant.LabelStatus standard;
    @ApiModelProperty(value = "对比算法执行结果", required = true, example = "NO_MOOD")
    private HeartRateConstant.LabelStatus normal;
}
