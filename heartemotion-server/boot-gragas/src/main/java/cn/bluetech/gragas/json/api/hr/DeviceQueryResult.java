package cn.bluetech.gragas.json.api.hr;

import javax.persistence.GeneratedValue;
import java.lang.Long;

import cn.bluetech.gragas.globals.HeartRateConstant;
import lombok.Data;
import com.alibaba.fastjson.annotation.JSONField;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import javax.persistence.Enumerated;
import cn.bluetech.gragas.globals.Constant.Is;
import java.lang.String;
import io.swagger.annotations.ApiModelProperty;
import javax.persistence.Id;


/**
 * 设备查询结果
 * @author xu
 * @date 2020-12-16 17:05:53
 */ 
@ApiModel
@Data
public class DeviceQueryResult{

    
    @ApiModelProperty(value = "id", required = true, example = "1")
    private Long id;
    
    @ApiModelProperty(value = "创建时间", required = true, example = "2018-03-12 21:32:33")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createDate;
    
    @ApiModelProperty(value = "设备名称", required = true, example = "DF3F34")
    private String deviceName;
    
    @ApiModelProperty(value = "设备标识", required = true, example = "DF3F34")
    private String deviceId;
    
    @ApiModelProperty(value = "佩戴人", required = true, example = "张三")
    private String wearer;
    
    @ApiModelProperty(value = "是否开启报警", required = true, example = "YES")
    private Is police;
    
    @ApiModelProperty(value = "客户端id", required = true, example = "dfd32wdfsdfsdfdsf")
    private String clientId;

    @ApiModelProperty(value = "设备状态", required = true, example = "NO_MOOD")
    private HeartRateConstant.LabelStatus labelStatus;

    @ApiModelProperty(value = "应对手段", required = true, example = "各种方法")
    private String means;

}