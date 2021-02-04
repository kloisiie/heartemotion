package cn.bluetech.gragas.pojo.hr;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


/**
 * 用户调试文件分页参数
 * @author xu
 * @date 2020-12-16 21:47:34
 */ 
@ApiModel
@Data
public class ClientDebugFilePageQueryParamDTO{


    @ApiModelProperty(value = "客户端ID", required = true, example = "sdfsdfsdfsdf")
    private String clientId;


}