package cn.bluetech.gragas.json.admin.hr;

import javax.persistence.Column;
import java.lang.Long;

import com.brframework.commonweb.json.BaseJson;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import java.lang.String;
import javax.validation.constraints.NotNull;
import io.swagger.annotations.ApiModelProperty;


/**
 * 算法调试添加参数
 * @author xu
 * @date 2020-12-16 16:50:52
 */ 
@ApiModel
@Data
public class ArithmeticDebugInsertParam extends BaseJson {

    
    @ApiModelProperty(value = "基准执行结果", required = true, example = "")
    @NotNull(message = "基准执行结果不能为空")
    private String standardResult;
    
    @ApiModelProperty(value = "执行算法ID", required = true, example = "")
    @NotNull(message = "执行算法ID不能为空")
    private Long arithmeticId;
    
    @ApiModelProperty(value = "客户端ID", required = true, example = "")
    @NotNull(message = "客户端ID不能为空")
    private String clientId;




}