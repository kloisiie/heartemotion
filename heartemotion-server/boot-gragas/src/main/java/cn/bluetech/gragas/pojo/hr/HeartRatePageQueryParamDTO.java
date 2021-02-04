package cn.bluetech.gragas.pojo.hr;

import cn.bluetech.gragas.globals.HeartRateConstant;
import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;


/**
 * 心率分页参数
 * @author xu
 * @date 2020-12-16 17:11:26
 */ 
@ApiModel
@Data
public class HeartRatePageQueryParamDTO {


    @ApiModelProperty(value = "心率时间筛选-开始(不传时为实时)", required = false, example = "2018-03-12 21:32:33")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime hrTimeStart;

    @ApiModelProperty(value = "心率时间筛选-结束(不传时为实时)", required = false, example = "2018-03-12 21:32:33")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime hrTimeEnd;

    @ApiModelProperty(value = "佩戴人", required = true, example = "")
    private String wearer;

    @ApiModelProperty(value = "设备标识", required = true, example = "")
    private String deviceId;
    @ApiModelProperty(value = "标注状态", required = true, example = "")
    private HeartRateConstant.LabelStatus labelStatus;
    @ApiModelProperty(value = "标注类型", required = true, example = "")
    private HeartRateConstant.LabelType labelType;




}