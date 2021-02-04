package cn.bluetech.gragas.json.api.hr;

import cn.bluetech.gragas.globals.HeartRateConstant;
import lombok.Data;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import java.lang.Integer;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;


/**
 * 心率查询结果
 * @author xu
 * @date 2020-12-16 17:11:27
 */ 
@ApiModel
@Data
public class HeartRateQueryResult{
    @ApiModelProperty(value = "心率数据列表", required = true)
    List<HeartRateQueryEntryResult> enters;

    @ApiModelProperty(value = "状态(无情绪、平稳、烦躁、高兴)", required = true, example = "NO_MOOD")
    private HeartRateConstant.LabelStatus labelStatus;

    @ApiModelProperty(value = "标注类型(手动标注、算法标注)", required = true, example = "")
    private HeartRateConstant.LabelType labelType;
    
}