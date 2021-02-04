package com.brframework.cms2.pojo.cms;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import java.lang.String;
import java.lang.Long;
import java.lang.Integer;
import io.swagger.annotations.ApiModelProperty;


/**
 * 菜单功能更新参数
 * @author xu
 * @date 2020-11-07 16:39:04
 */ 
@ApiModel
@Data
public class CmsMenuFunUpdateParamDTO{

    
    @ApiModelProperty(value = "名称", required = true, example = "功能")
    private String name;
    
    @ApiModelProperty(value = "子菜单ID", required = true, example = "1")
    private Long menuSubId;
    
    @ApiModelProperty(value = "排序", required = true, example = "1")
    private Integer sort;
    
    @ApiModelProperty(value = "权限标识", required = true, example = "role_add")
    private String role;




}