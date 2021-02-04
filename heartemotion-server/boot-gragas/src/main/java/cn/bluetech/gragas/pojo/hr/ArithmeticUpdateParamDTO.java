package cn.bluetech.gragas.pojo.hr;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import java.lang.String;
import io.swagger.annotations.ApiModelProperty;


/**
 * 算法更新参数
 * @author xu
 * @date 2020-12-16 16:37:39
 */ 
@ApiModel
@Data
public class ArithmeticUpdateParamDTO{

    
    @ApiModelProperty(value = "算法名称", required = true, example = "")
    private String name;

    @ApiModelProperty(value = "算法访问地址", required = true, example = "")
    private String serverUrl;

    @ApiModelProperty(value = "排序", required = true, example = "")
    private Integer sort;


}