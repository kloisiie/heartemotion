package com.brframework.cms2.pojo.cms;

import com.brframework.cms2.globals.CmsConstant.Is;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import java.lang.String;
import java.lang.Integer;
import io.swagger.annotations.ApiModelProperty;
import com.brframework.cms2.globals.CmsConstant.MenuType;


/**
 * 菜单更新参数
 * @author xu
 * @date 2020-11-07 16:34:16
 */ 
@ApiModel
@Data
public class CmsMenuUpdateParamDTO{

    
    @ApiModelProperty(value = "名称", required = true, example = "用户管理")
    private String name;
    
    @ApiModelProperty(value = "图标", required = true, example = "icon")
    private String icon;
    
    @ApiModelProperty(value = "排序", required = true, example = "1")
    private Integer sort;
    
    @ApiModelProperty(value = "类型", required = true, example = "LEFT")
    private MenuType type;
    
    @ApiModelProperty(value = "是否隐藏", required = true, example = "YES")
    private Is hide;




}