package cn.bluetech.gragas.pojo.hr;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import javax.persistence.Column;
import java.lang.String;
import java.lang.Long;
import io.swagger.annotations.ApiModelProperty;


/**
 * 算法调试添加参数
 * @author xu
 * @date 2020-12-16 16:50:51
 */ 
@ApiModel
@Data
public class ArithmeticDebugInsertParamDTO{

    
    @ApiModelProperty(value = "基准执行结果", required = true, example = "")
    private String standardResult;
    
    @ApiModelProperty(value = "执行算法ID", required = true, example = "")
    private Long arithmeticId;
    
    @ApiModelProperty(value = "客户端ID", required = true, example = "")
    private String clientId;




}