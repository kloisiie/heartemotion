package cn.bluetech.gragas.json.admin.client;

import com.brframework.commonweb.json.BaseJson;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import java.lang.String;
import javax.validation.constraints.NotNull;
import io.swagger.annotations.ApiModelProperty;


/**
 * 操作日志添加参数
 * @author xu
 * @date 2020-12-16 17:40:35
 */ 
@ApiModel
@Data
public class OptionLogInsertParam extends BaseJson {

    
    @ApiModelProperty(value = "客户端ID", required = true, example = "")
    @NotNull(message = "客户端ID不能为空")
    private String clientId;
    
    @ApiModelProperty(value = "调用接口", required = true, example = "")
    @NotNull(message = "调用接口不能为空")
    private String callApi;
    
    @ApiModelProperty(value = "IP", required = true, example = "")
    @NotNull(message = "IP不能为空")
    private String ip;




}