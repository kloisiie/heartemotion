package cn.bluetech.gragas.pojo.hr;

import javax.persistence.Column;
import cn.bluetech.gragas.globals.HeartRateConstant.DebugStatus;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import javax.persistence.Enumerated;
import java.lang.String;
import io.swagger.annotations.ApiModelProperty;


/**
 * 算法调试更新参数
 * @author xu
 * @date 2020-12-16 16:50:51
 */ 
@ApiModel
@Data
public class ArithmeticDebugUpdateParamDTO{

    
    @ApiModelProperty(value = "调试算法执行结果", required = true, example = "")
    private String debugResult;
    
    @ApiModelProperty(value = "执行状态(进行中、成功、失败)", required = true, example = "")
    private DebugStatus status;




}