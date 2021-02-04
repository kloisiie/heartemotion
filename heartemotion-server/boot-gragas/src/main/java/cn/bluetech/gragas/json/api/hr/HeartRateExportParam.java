package cn.bluetech.gragas.json.api.hr;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @author xu
 * @date 2020/12/21 15:34
 */
@ApiModel
@Data
public class HeartRateExportParam {

    @ApiModelProperty(value = "心率时间筛选-开始", required = true, example = "2018-03-12 21:32:33")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "请输入正确的时间")
    private LocalDateTime hrTimeStart;

    @ApiModelProperty(value = "心率时间筛选-结束", required = true, example = "2018-03-12 21:32:33")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "请输入正确的时间")
    private LocalDateTime hrTimeEnd;

}
