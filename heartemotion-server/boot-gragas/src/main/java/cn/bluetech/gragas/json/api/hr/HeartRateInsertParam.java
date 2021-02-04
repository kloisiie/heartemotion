package cn.bluetech.gragas.json.api.hr;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import java.lang.String;
import javax.validation.constraints.NotNull;
import java.lang.Integer;
import io.swagger.annotations.ApiModelProperty;


/**
 * 心率添加参数
 * @author xu
 * @date 2020-12-16 17:11:27
 */ 
@ApiModel
@Data
public class HeartRateInsertParam{

    
    @ApiModelProperty(value = "产生时间", required = true, example = "2020-12-16 22:25:12")
    @NotNull(message = "产生时间不能为空")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime hrTime;

    @ApiModelProperty(value = "心率值", required = true, example = "80")
    @NotNull(message = "心率值不能为空")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Integer hrValue;

}