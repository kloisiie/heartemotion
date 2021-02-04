package cn.bluetech.gragas.pojo.hr;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


/**
 * 算法分页参数
 * @author xu
 * @date 2020-12-16 16:37:39
 */ 
@ApiModel
@Data
public class ArithmeticPageQueryParamDTO{



    @ApiModelProperty(value = "算法名称", required = true, example = "算法A")
    private String name;

}