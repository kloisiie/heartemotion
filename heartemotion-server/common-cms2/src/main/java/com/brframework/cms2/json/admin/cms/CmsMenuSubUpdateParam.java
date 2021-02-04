package com.brframework.cms2.json.admin.cms;

import java.lang.Long;
import com.brframework.cms2.globals.CmsConstant.Is;
import com.brframework.commonweb.json.BaseJson;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import java.lang.String;
import javax.validation.constraints.NotNull;
import java.lang.Integer;
import io.swagger.annotations.ApiModelProperty;


/**
 * 子菜单修改参数
 * @author xu
 * @date 2020-11-07 16:37:38
 */ 
@ApiModel
@Data
public class CmsMenuSubUpdateParam extends BaseJson {

    
    @ApiModelProperty(value = "名称", required = true, example = "子菜单")
    @NotNull(message = "名称不能为空")
    private String name;
    
    @ApiModelProperty(value = "图标", required = true, example = "icon")
    @NotNull(message = "图标不能为空")
    private String icon;
    
    @ApiModelProperty(value = "排序", required = true, example = "1")
    @NotNull(message = "排序不能为空")
    private Integer sort;
    
    @ApiModelProperty(value = "主菜单ID", required = true, example = "1")
    @NotNull(message = "主菜单ID不能为空")
    private Long menuId;
    
    @ApiModelProperty(value = "路由页面", required = true, example = "1")
    @NotNull(message = "路由页面不能为空")
    private Long[] pageId;
    
    @ApiModelProperty(value = "权限标识", required = true, example = "role_add")
    @NotNull(message = "权限标识不能为空")
    private String role;
    
    @ApiModelProperty(value = "是否隐藏", required = true, example = "YES")
    @NotNull(message = "是否隐藏不能为空")
    private Is hide;




}