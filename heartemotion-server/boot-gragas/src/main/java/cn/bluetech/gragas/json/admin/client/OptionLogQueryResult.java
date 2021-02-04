package cn.bluetech.gragas.json.admin.client;

import javax.persistence.GeneratedValue;
import java.lang.Long;

import com.brframework.commonweb.json.BaseJson;
import lombok.Data;
import com.alibaba.fastjson.annotation.JSONField;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import java.lang.String;
import io.swagger.annotations.ApiModelProperty;
import javax.persistence.Id;


/**
 * 操作日志查询结果
 * @author xu
 * @date 2020-12-16 17:40:35
 */ 
@ApiModel
@Data
public class OptionLogQueryResult extends BaseJson {

    
    @ApiModelProperty(value = "id", required = true, example = "1")
    private Long id;
    
    @ApiModelProperty(value = "创建时间", required = true, example = "2018-03-12 21:32:33")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createDate;
    
    @ApiModelProperty(value = "客户端ID", required = true, example = "")
    private String clientId;
    
    @ApiModelProperty(value = "调用接口", required = true, example = "")
    private String callApi;
    
    @ApiModelProperty(value = "IP", required = true, example = "")
    private String ip;




}