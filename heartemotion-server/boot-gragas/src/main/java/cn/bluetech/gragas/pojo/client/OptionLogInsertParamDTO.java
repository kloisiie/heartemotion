package cn.bluetech.gragas.pojo.client;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import java.lang.String;
import io.swagger.annotations.ApiModelProperty;


/**
 * 操作日志添加参数
 * @author xu
 * @date 2020-12-16 17:40:35
 */ 
@ApiModel
@Data
public class OptionLogInsertParamDTO{

    
    @ApiModelProperty(value = "客户端ID", required = true, example = "")
    private String clientId;
    
    @ApiModelProperty(value = "调用接口", required = true, example = "")
    private String callApi;
    
    @ApiModelProperty(value = "IP", required = true, example = "")
    private String ip;




}