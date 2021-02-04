package cn.bluetech.gragas.pojo.client;

import lombok.Data;
import com.alibaba.fastjson.annotation.JSONField;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import java.lang.String;
import io.swagger.annotations.ApiModelProperty;


/**
 * 操作日志分页参数
 * @author xu
 * @date 2020-12-16 17:40:35
 */ 
@ApiModel
@Data
public class OptionLogPageQueryParamDTO{

    
    @ApiModelProperty(value = "创建时间", required = true, example = "2018-03-12 21:32:33")
    private LocalDateTime createDateStart;
    @ApiModelProperty(value = "创建时间", required = true, example = "2018-03-12 21:32:33")
    private LocalDateTime createDateEnd;
    
    @ApiModelProperty(value = "客户端ID", required = true, example = "")
    private String clientId;
    
    @ApiModelProperty(value = "调用接口", required = true, example = "")
    private String callApi;
    
    @ApiModelProperty(value = "IP", required = true, example = "")
    private String ip;




}