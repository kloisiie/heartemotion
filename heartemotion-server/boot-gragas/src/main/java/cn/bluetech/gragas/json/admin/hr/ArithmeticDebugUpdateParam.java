package cn.bluetech.gragas.json.admin.hr;

import javax.persistence.Column;
import cn.bluetech.gragas.globals.HeartRateConstant.DebugStatus;
import com.brframework.commonweb.json.BaseJson;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import javax.persistence.Enumerated;
import java.lang.String;
import javax.validation.constraints.NotNull;
import io.swagger.annotations.ApiModelProperty;


/**
 * 算法调试修改参数
 * @author xu
 * @date 2020-12-16 16:50:52
 */ 
@ApiModel
@Data
public class ArithmeticDebugUpdateParam extends BaseJson {

    
    @ApiModelProperty(value = "调试算法执行结果", required = true, example = "")
    @NotNull(message = "调试算法执行结果不能为空")
    private String debugResult;
    
    @ApiModelProperty(value = "执行状态(进行中、成功、失败)", required = true, example = "")
    @NotNull(message = "执行状态(进行中、成功、失败)不能为空")
    private DebugStatus status;




}