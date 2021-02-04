package com.brframework.cms2.json.admin.cms;

import java.lang.Long;

import com.brframework.commonweb.json.BaseJson;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import java.lang.String;
import javax.validation.constraints.NotNull;
import java.lang.Integer;
import io.swagger.annotations.ApiModelProperty;


/**
 * 菜单功能修改参数
 * @author xu
 * @date 2020-11-07 16:39:04
 */ 
@ApiModel
@Data
public class CmsMenuFunUpdateParam extends BaseJson {

    
    @ApiModelProperty(value = "名称", required = true, example = "功能")
    @NotNull(message = "名称不能为空")
    private String name;
    
    @ApiModelProperty(value = "子菜单ID", required = true, example = "1")
    @NotNull(message = "子菜单ID不能为空")
    private Long menuSubId;
    
    @ApiModelProperty(value = "排序", required = true, example = "1")
    @NotNull(message = "排序不能为空")
    private Integer sort;
    
    @ApiModelProperty(value = "权限标识", required = true, example = "role_add")
    @NotNull(message = "权限标识不能为空")
    private String role;




}