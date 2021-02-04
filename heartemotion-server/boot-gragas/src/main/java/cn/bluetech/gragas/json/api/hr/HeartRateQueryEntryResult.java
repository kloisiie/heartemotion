package cn.bluetech.gragas.json.api.hr;

import cn.bluetech.gragas.globals.HeartRateConstant;
import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;

/**
 * @author xu
 * @date 2020/12/16 20:51
 */
@ApiModel
@Data
public class HeartRateQueryEntryResult {
    @ApiModelProperty(value = "产生时间", required = true, example = "2020-12-16 20:52:56")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime hrTime;
    @ApiModelProperty(value = "心率值", required = true, example = "82")
    private Integer hrValue;
}
