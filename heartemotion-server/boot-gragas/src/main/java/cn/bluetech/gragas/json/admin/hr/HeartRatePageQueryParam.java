package cn.bluetech.gragas.json.admin.hr;

import cn.bluetech.gragas.globals.HeartRateConstant;
import com.alibaba.fastjson.annotation.JSONField;
import com.brframework.commonweb.json.BaseJson;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author xu
 * @date 2020/12/18 16:06
 */
@ApiModel
@Data
public class HeartRatePageQueryParam extends BaseJson {

    @ApiModelProperty(value = "心率时间", required = true, example = "")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private List<LocalDateTime> hrTime;
    @ApiModelProperty(value = "设备标识", required = true, example = "")
    private String deviceId;
    @ApiModelProperty(value = "佩戴人", required = true, example = "")
    private String wearer;
    @ApiModelProperty(value = "标注状态", required = true, example = "")
    private List<String> labelStatus;
    @ApiModelProperty(value = "标注类型", required = true, example = "")
    private List<String> labelType;

}
