package cn.bluetech.gragas.json.api.hr;

import javax.persistence.GeneratedValue;
import java.lang.Long;
import lombok.Data;
import com.alibaba.fastjson.annotation.JSONField;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import java.lang.String;
import java.lang.Integer;
import io.swagger.annotations.ApiModelProperty;
import javax.persistence.Id;


/**
 * 心率详情结果
 * @author xu
 * @date 2020-12-16 17:11:27
 */ 
@ApiModel
@Data
public class HeartRateDetailResult{

    
    @ApiModelProperty(value = "id", required = true, example = "1")
    private Long id;
    
    @ApiModelProperty(value = "创建时间", required = true, example = "2018-03-12 21:32:33")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createDate;
    
    @ApiModelProperty(value = "产生时间", required = true, example = "")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime hrTime;
    
    @ApiModelProperty(value = "设备标识", required = true, example = "")
    private String deviceId;
    
    @ApiModelProperty(value = "心率值", required = true, example = "")
    private Integer hrValue;




}