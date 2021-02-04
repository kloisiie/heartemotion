package cn.bluetech.gragas.pojo.hr;

import lombok.Data;
import com.alibaba.fastjson.annotation.JSONField;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


/**
 * 心率分页参数
 * @author xu
 * @date 2020-12-16 17:11:26
 */ 
@ApiModel
@Data
public class HeartRateListQueryParamDTO {


    @ApiModelProperty(value = "心率时间筛选-开始(不传时为实时)", required = false, example = "2018-03-12 21:32:33")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime hrTimeStart;

    @ApiModelProperty(value = "心率时间筛选-结束(不传时为实时)", required = false, example = "2018-03-12 21:32:33")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime hrTimeEnd;




}