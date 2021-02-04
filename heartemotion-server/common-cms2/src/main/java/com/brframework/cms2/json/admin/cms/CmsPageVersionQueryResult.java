package com.brframework.cms2.json.admin.cms;

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
 * 页面版本查询结果
 * @author xu
 * @date 2020-11-07 18:37:22
 */ 
@ApiModel
@Data
public class CmsPageVersionQueryResult extends BaseJson {

    
    @ApiModelProperty(value = "id", required = true, example = "1")
    private Long id;
    
    @ApiModelProperty(value = "创建时间", required = true, example = "2018-03-12 21:32:33")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createDate;
    
    @ApiModelProperty(value = "名称", required = true, example = "首次创建")
    private String name;
    
    @ApiModelProperty(value = "创建人", required = true, example = "zhangsan")
    private String owner;

    @ApiModelProperty(value = "版本号", required = true, example = "232323")
    private String versionName;
    
    @ApiModelProperty(value = "所属页面", required = true, example = "1")
    private Long pageId;




}