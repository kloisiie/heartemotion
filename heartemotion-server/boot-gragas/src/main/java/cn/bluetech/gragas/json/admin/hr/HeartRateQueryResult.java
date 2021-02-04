package cn.bluetech.gragas.json.admin.hr;

import cn.bluetech.gragas.globals.HeartRateConstant;
import com.alibaba.fastjson.annotation.JSONField;
import com.brframework.commonweb.json.BaseJson;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author xu
 * @date 2020/12/18 16:06
 */
@ApiModel
@Data
public class HeartRateQueryResult extends BaseJson {

    @ApiModelProperty(value = "ID", required = true, example = "1")
    private Long id;
    @ApiModelProperty(value = "产生时间", required = true, example = "")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime hrTime;
    @ApiModelProperty(value = "设备标识", required = true, example = "")
    private String deviceId;
    @ApiModelProperty(value = "佩戴人", required = true, example = "")
    private String wearer;
    @ApiModelProperty(value = "心率值", required = true, example = "")
    private Integer hrValue;
    @ApiModelProperty(value = "标注状态", required = true, example = "")
    private HeartRateConstant.LabelStatus labelStatus;
    @ApiModelProperty(value = "标注状态", required = true, example = "")
    private String labelStatusName;
    @ApiModelProperty(value = "应对手段", required = true, example = "各种方法")
    private String means;
    @ApiModelProperty(value = "标注类型", required = true, example = "")
    private HeartRateConstant.LabelType labelType;
    @ApiModelProperty(value = "标注类型", required = true, example = "")
    private String labelTypeName;

}
