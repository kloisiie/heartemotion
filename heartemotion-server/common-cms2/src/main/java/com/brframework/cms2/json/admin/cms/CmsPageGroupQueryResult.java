package com.brframework.cms2.json.admin.cms;

import javax.persistence.GeneratedValue;
import java.lang.Long;

import com.brframework.commonweb.json.BaseJson;
import lombok.Data;
import com.alibaba.fastjson.annotation.JSONField;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import java.lang.String;
import java.lang.Integer;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import javax.persistence.Id;


/**
 * 页面组查询结果
 * @author xu
 * @date 2020-11-07 18:01:00
 */ 
@ApiModel
@Data
public class CmsPageGroupQueryResult extends BaseJson {

    
    @ApiModelProperty(value = "id", required = true, example = "1")
    private Long id;
    
    @ApiModelProperty(value = "创建时间", required = true, example = "2018-03-12 21:32:33")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createDate;
    
    @ApiModelProperty(value = "组名称", required = true, example = "用户管理")
    private String name;
    
    @ApiModelProperty(value = "排序", required = true, example = "1")
    private Integer sort;

    @ApiModelProperty(value = "页面列表", required = true)
    private List<CmsPageQueryResult> pageList;

    @ApiModelProperty(value = "级别", required = true)
    private Integer level = 1;

}