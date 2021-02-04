package cn.bluetech.gragas.json.admin.hr;

import com.brframework.commonweb.json.BaseJson;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


/**
 * 算法查询参数
 * @author xu
 * @date 2020-12-16 16:37:39
 */ 
@ApiModel
@Data
public class ArithmeticPageQueryParam extends BaseJson {


    @ApiModelProperty(value = "算法名称", required = true, example = "算法A")
    private String name;


}