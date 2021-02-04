package cn.bluetech.gragas.json.api.hr;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import javax.persistence.Enumerated;
import cn.bluetech.gragas.globals.Constant.Is;
import java.lang.String;
import javax.validation.constraints.NotNull;
import io.swagger.annotations.ApiModelProperty;


/**
 * 设备修改参数
 * @author xu
 * @date 2020-12-16 17:05:53
 */ 
@ApiModel
@Data
public class DeviceUpdateParam{

    @ApiModelProperty(value = "设备名称(空为不更新)", required = false, example = "DF3F34")
    private String deviceName;
    
    @ApiModelProperty(value = "佩戴人(空为不更新)", required = false, example = "张三")
    private String wearer;
    
    @ApiModelProperty(value = "是否开启报警(空为不更新)", required = false, example = "YES")
    private Is police;




}