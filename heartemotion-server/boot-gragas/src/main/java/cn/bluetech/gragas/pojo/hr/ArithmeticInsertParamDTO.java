package cn.bluetech.gragas.pojo.hr;

import cn.bluetech.gragas.globals.HeartRateConstant;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import java.lang.String;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;


/**
 * 算法添加参数
 * @author xu
 * @date 2020-12-16 16:37:39
 */ 
@ApiModel
@Data
public class ArithmeticInsertParamDTO{

    
    @ApiModelProperty(value = "算法名称", required = true, example = "")
    private String name;

    @ApiModelProperty(value = "算法类型(基准算法、普通算法)", required = true, example = "")
    private HeartRateConstant.ArithmeticType type;

    @ApiModelProperty(value = "算法访问地址", required = true, example = "")
    private String serverUrl;

    @ApiModelProperty(value = "排序", required = true, example = "")
    private Integer sort;


}