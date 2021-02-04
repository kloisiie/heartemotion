package com.brframework.cms2.json.admin.cms;

import com.brframework.cms2.globals.CmsConstant.Is;
import com.brframework.commonweb.json.BaseJson;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import java.lang.String;
import javax.validation.constraints.NotNull;
import java.lang.Integer;
import io.swagger.annotations.ApiModelProperty;
import com.brframework.cms2.globals.CmsConstant.MenuType;


/**
 * 菜单修改参数
 * @author xu
 * @date 2020-11-07 16:34:16
 */ 
@ApiModel
@Data
public class CmsMenuUpdateParam extends BaseJson {

    
    @ApiModelProperty(value = "名称", required = true, example = "用户管理")
    @NotNull(message = "名称不能为空")
    private String name;
    
    @ApiModelProperty(value = "图标", required = true, example = "icon")
    @NotNull(message = "图标不能为空")
    private String icon;
    
    @ApiModelProperty(value = "排序", required = true, example = "1")
    @NotNull(message = "排序不能为空")
    private Integer sort;
    
    @ApiModelProperty(value = "类型", required = true, example = "LEFT")
    @NotNull(message = "类型不能为空")
    private MenuType type;
    
    @ApiModelProperty(value = "是否隐藏", required = true, example = "YES")
    @NotNull(message = "是否隐藏不能为空")
    private Is hide;




}