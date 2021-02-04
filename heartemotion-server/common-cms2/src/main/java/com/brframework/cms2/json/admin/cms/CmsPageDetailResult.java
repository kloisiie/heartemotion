package com.brframework.cms2.json.admin.cms;

import com.alibaba.fastjson.annotation.JSONField;
import java.time.LocalDateTime;
import java.lang.Integer;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import java.lang.Long;
import com.brframework.cms2.globals.CmsConstant.PageType;
import com.brframework.commonweb.json.BaseJson;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import javax.persistence.Enumerated;
import java.lang.String;
import io.swagger.annotations.ApiModelProperty;
import javax.persistence.Id;


/**
 * 页面详情结果
 * @author xu
 * @date 2020-11-07 17:59:48
 */ 
@ApiModel
@Data
public class CmsPageDetailResult extends BaseJson {

    
    @ApiModelProperty(value = "id", required = true, example = "1")
    private Long id;
    
    @ApiModelProperty(value = "创建时间", required = true, example = "2018-03-12 21:32:33")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createDate;
    
    @ApiModelProperty(value = "页面名称", required = true, example = "用户列表")
    private String name;
    
    @ApiModelProperty(value = "页面类型(静态路由、动态路由)", required = true, example = "STATIC")
    private PageType type;
    
    @ApiModelProperty(value = "组ID", required = true, example = "1")
    private Long groupId;

    @ApiModelProperty(value = "路由名称", required = true, example = "1")
    private String routeName;
    
    @ApiModelProperty(value = "排序", required = true, example = "1")
    private Integer sort;
    
    @ApiModelProperty(value = "关联路由", required = true, example = "1")
    private String route;
    
    @ApiModelProperty(value = "页面内容", required = true, example = "1111")
    private String content;




}