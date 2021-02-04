package cn.bluetech.gragas.json.admin.hr;

import com.brframework.commonweb.json.BaseJson;
import lombok.Data;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import java.lang.String;
import io.swagger.annotations.ApiModelProperty;


/**
 * 报警记录查询参数
 * @author xu
 * @date 2020-12-16 17:23:08
 */ 
@ApiModel
@Data
public class PolicePageQueryParam extends BaseJson {

    @ApiModelProperty(value = "设备标识", required = true, example = "")
    private String deviceId;




}