package cn.bluetech.gragas.json.api.hr;

import com.brframework.commonweb.json.BaseJson;
import lombok.Data;
import java.time.LocalDateTime;
import cn.bluetech.gragas.globals.HeartRateConstant.LabelStatus;
import io.swagger.annotations.ApiModel;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import io.swagger.annotations.ApiModelProperty;
import cn.bluetech.gragas.globals.HeartRateConstant.LabelType;


/**
 * 标注添加参数
 * @author xu
 * @date 2020-12-16 17:17:35
 */ 
@ApiModel
@Data
public class LabelInsertParam extends BaseJson {

    @ApiModelProperty(value = "设备标识", required = true, example = "AD43FD")
    @NotNull(message = "设备标识不能为空")
    private String deviceId;
    
    @ApiModelProperty(value = "标注时间开始时间", required = true, example = "2020-12-16 21:22:03")
    @NotNull(message = "标注时间不能为空")
    private LocalDateTime labelTimeStart;

    @ApiModelProperty(value = "标注时间结束时间", required = true, example = "2020-12-16 21:22:03")
    @NotNull(message = "标注时间不能为空")
    private LocalDateTime labelTimeEnd;
    
    @ApiModelProperty(value = "标注状态(无情绪、平稳、烦躁、高兴)", required = true, example = "NO_MOOD")
    @NotNull(message = "标注状态(无情绪、平稳、烦躁、高兴)不能为空")
    private LabelStatus labelStatus;

    @ApiModelProperty(value = "应对手段", required = true, example = "各种方法")
    @NotNull(message = "应对手段不能为空")
    private String means;

    @ApiModelProperty(value = "是否覆盖(否的情况下，如果所选时间已有标注的情况下会失败)", example = "false")
    private boolean override;
}