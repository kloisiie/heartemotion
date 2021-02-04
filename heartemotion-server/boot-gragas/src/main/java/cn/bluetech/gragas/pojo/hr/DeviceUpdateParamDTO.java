package cn.bluetech.gragas.pojo.hr;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import javax.persistence.Enumerated;
import cn.bluetech.gragas.globals.Constant.Is;
import java.lang.String;
import io.swagger.annotations.ApiModelProperty;


/**
 * 设备更新参数
 * @author xu
 * @date 2020-12-16 17:05:53
 */ 
@ApiModel
@Data
public class DeviceUpdateParamDTO{

    @ApiModelProperty(value = "设备名称(空为不更新)", required = false, example = "DF3F34")
    private String deviceName;
    
    @ApiModelProperty(value = "佩戴人", required = true, example = "")
    private String wearer;
    
    @ApiModelProperty(value = "是否开启报警", required = true, example = "")
    private Is police;




}