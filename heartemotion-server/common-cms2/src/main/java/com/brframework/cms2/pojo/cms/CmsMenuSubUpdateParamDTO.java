package com.brframework.cms2.pojo.cms;

import java.lang.Long;
import com.brframework.cms2.globals.CmsConstant.Is;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import java.lang.String;
import java.lang.Integer;
import io.swagger.annotations.ApiModelProperty;


/**
 * 子菜单更新参数
 * @author xu
 * @date 2020-11-07 16:37:37
 */ 
@ApiModel
@Data
public class CmsMenuSubUpdateParamDTO{

    
    @ApiModelProperty(value = "名称", required = true, example = "子菜单")
    private String name;
    
    @ApiModelProperty(value = "图标", required = true, example = "icon")
    private String icon;
    
    @ApiModelProperty(value = "排序", required = true, example = "1")
    private Integer sort;
    
    @ApiModelProperty(value = "主菜单ID", required = true, example = "1")
    private Long menuId;
    
    @ApiModelProperty(value = "路由页面", required = true, example = "1")
    private Long pageId;
    
    @ApiModelProperty(value = "权限标识", required = true, example = "role_add")
    private String role;
    
    @ApiModelProperty(value = "是否隐藏", required = true, example = "YES")
    private Is hide;




}