package cn.bluetech.gragas.pojo.hr;

import lombok.Data;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import java.lang.String;
import java.lang.Integer;
import io.swagger.annotations.ApiModelProperty;


/**
 * 心率添加参数
 * @author xu
 * @date 2020-12-16 17:11:26
 */ 
@ApiModel
@Data
public class HeartRateInsertParamDTO{

    
    @ApiModelProperty(value = "产生时间", required = true, example = "")
    private LocalDateTime hrTime;
    
    @ApiModelProperty(value = "设备标识", required = true, example = "")
    private String deviceId;
    
    @ApiModelProperty(value = "心率值", required = true, example = "")
    private Integer hrValue;

    @ApiModelProperty(value = "佩戴人", required = true, example = "")
    private String wearer;


}