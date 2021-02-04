package cn.bluetech.gragas.json.api.hr;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import java.lang.String;
import io.swagger.annotations.ApiModelProperty;


/**
 * 设备查询参数
 * @author xu
 * @date 2020-12-16 17:05:53
 */ 
@ApiModel
@Data
public class DevicePageQueryParam{

    
    @ApiModelProperty(value = "客户端id", required = true, example = "")
    private String clientId;




}