package cn.bluetech.gragas.json.admin.hr;

import com.brframework.commonweb.json.BaseJson;
import lombok.Data;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import java.lang.String;
import javax.validation.constraints.NotNull;
import io.swagger.annotations.ApiModelProperty;


/**
 * 报警记录添加参数
 * @author xu
 * @date 2020-12-16 17:23:08
 */ 
@ApiModel
@Data
public class PoliceInsertParam extends BaseJson {

    
    @ApiModelProperty(value = "报警时间", required = true, example = "")
    @NotNull(message = "报警时间不能为空")
    private LocalDateTime policeTime;
    
    @ApiModelProperty(value = "设备标识", required = true, example = "")
    @NotNull(message = "设备标识不能为空")
    private String deviceId;




}