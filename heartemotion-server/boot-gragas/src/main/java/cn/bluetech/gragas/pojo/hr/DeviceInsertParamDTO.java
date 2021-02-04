package cn.bluetech.gragas.pojo.hr;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import javax.persistence.Enumerated;
import cn.bluetech.gragas.globals.Constant.Is;
import java.lang.String;
import io.swagger.annotations.ApiModelProperty;


/**
 * 设备添加参数
 * @author xu
 * @date 2020-12-16 17:05:53
 */ 
@ApiModel
@Data
public class DeviceInsertParamDTO{

    
    @ApiModelProperty(value = "客户端id", required = true, example = "")
    private String clientId;
    
    @ApiModelProperty(value = "设备标识", required = true, example = "")
    private String deviceId;
    
    @ApiModelProperty(value = "设备名称", required = true, example = "")
    private String deviceName;
    
    @ApiModelProperty(value = "佩戴人", required = true, example = "")
    private String wearer;
    
    @ApiModelProperty(value = "是否开启报警", required = true, example = "")
    private Is police;




}