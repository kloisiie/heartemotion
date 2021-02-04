package cn.bluetech.gragas.json.admin.hr;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import java.lang.Long;

import com.brframework.commonweb.json.BaseJson;
import lombok.Data;
import com.alibaba.fastjson.annotation.JSONField;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import java.lang.String;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import javax.persistence.Id;


/**
 * 平台调试文件详情结果
 * @author xu
 * @date 2020-12-16 21:51:27
 */ 
@ApiModel
@Data
public class PlatformDebugFileDetailResult extends BaseJson {

    
    @ApiModelProperty(value = "id", required = true, example = "1")
    private Long id;
    
    @ApiModelProperty(value = "创建时间", required = true, example = "2018-03-12 21:32:33")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createDate;
    
    @ApiModelProperty(value = "文件名", required = true, example = "abc.txt")
    private String fileName;
    
    @ApiModelProperty(value = "文件内容", required = true, example = "abcdfdsafsdf")
    private List<String> content;
    
    @ApiModelProperty(value = "描述", required = true, example = "心率文件")
    private String details;
    
    @ApiModelProperty(value = "数据开始时间", required = true, example = "2018-03-12 21:32:33")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;
    
    @ApiModelProperty(value = "数据结束时间", required = true, example = "2018-03-12 21:32:33")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;




}