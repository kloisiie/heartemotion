package cn.bluetech.gragas.pojo.hr;

import lombok.Data;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import java.lang.String;
import io.swagger.annotations.ApiModelProperty;


/**
 * 报警记录添加参数
 * @author xu
 * @date 2020-12-16 17:23:08
 */ 
@ApiModel
@Data
public class PoliceInsertParamDTO{

    
    @ApiModelProperty(value = "报警时间", required = true, example = "")
    private LocalDateTime policeTime;
    
    @ApiModelProperty(value = "设备标识", required = true, example = "")
    private String deviceId;




}