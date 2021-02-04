package cn.bluetech.gragas.json.admin.client;

import com.brframework.commonweb.json.BaseJson;
import lombok.Data;
import com.alibaba.fastjson.annotation.JSONField;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import java.lang.String;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;


/**
 * 操作日志查询参数
 * @author xu
 * @date 2020-12-16 17:40:35
 */ 
@ApiModel
@Data
public class OptionLogPageQueryParam extends BaseJson {

    
    @ApiModelProperty(value = "创建时间", required = true, example = "2018-03-12 21:32:33")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private List<LocalDateTime> createDate;
    
    @ApiModelProperty(value = "客户端ID", required = true, example = "")
    private String clientId;
    
    @ApiModelProperty(value = "调用接口", required = true, example = "")
    private String callApi;
    
    @ApiModelProperty(value = "IP", required = true, example = "")
    private String ip;




}