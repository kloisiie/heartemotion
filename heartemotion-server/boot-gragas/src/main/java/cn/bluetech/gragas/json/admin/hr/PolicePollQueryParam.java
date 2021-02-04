package cn.bluetech.gragas.json.admin.hr;

import com.alibaba.fastjson.annotation.JSONField;
import com.brframework.commonweb.json.BaseJson;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;


/**
 * 报警记录查询参数
 * @author xu
 * @date 2020-12-16 17:23:08
 */ 
@ApiModel
@Data
public class PolicePollQueryParam extends BaseJson {

    @ApiModelProperty(value = "设备标识(多个设备用,隔开)", required = true, example = "")
    private String deviceIds;

    @ApiModelProperty(value = "时间标记(将当前接口的返回值作为参数)", required = true, example = "2018-03-12 21:32:33")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timeMark;

}