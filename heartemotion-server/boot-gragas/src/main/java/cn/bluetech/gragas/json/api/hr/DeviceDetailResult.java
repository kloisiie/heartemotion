package cn.bluetech.gragas.json.api.hr;

import java.lang.Long;

import cn.bluetech.gragas.globals.HeartRateConstant;
import lombok.Data;
import com.alibaba.fastjson.annotation.JSONField;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import cn.bluetech.gragas.globals.Constant.Is;
import java.lang.String;
import io.swagger.annotations.ApiModelProperty;


/**
 * 设备详情结果
 * @author xu
 * @date 2020-12-16 17:05:53
 */ 
@ApiModel
@Data
public class DeviceDetailResult{

    
    @ApiModelProperty(value = "id", required = true, example = "1")
    private Long id;
    
    @ApiModelProperty(value = "创建时间", required = true, example = "2018-03-12 21:32:33")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createDate;
    
    @ApiModelProperty(value = "设备名称", required = true, example = "sdfsdf")
    private String deviceName;
    
    @ApiModelProperty(value = "设备标识", required = true, example = "dfsdfsdf")
    private String deviceId;
    
    @ApiModelProperty(value = "佩戴人", required = true, example = "acvdsf")
    private String wearer;
    
    @ApiModelProperty(value = "是否开启报警", required = true, example = "YES")
    private Is police;
    
    @ApiModelProperty(value = "客户端id", required = true, example = "fsd23sdfsdfsdf")
    private String clientId;

    @ApiModelProperty(value = "心率产生时间", required = true, example = "2020-12-16 20:52:56")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime hrTime;

    @ApiModelProperty(value = "应对手段", required = true, example = "各种方法")
    private String means;

    @ApiModelProperty(value = "心率值", required = true, example = "82")
    private Integer hrValue;

    @ApiModelProperty(value = "状态(无情绪、平稳、烦躁、高兴)", required = true, example = "NO_MOOD")
    private HeartRateConstant.LabelStatus labelStatus;

    @ApiModelProperty(value = "报警记录数量", required = true, example = "82")
    private Long policeCount;
}