package com.brframework.commonwebadmin.pojo;

import com.brframework.commonwebadmin.globals.AdminConstant;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author xu
 * @date 2020/6/12 12:56
 */
@Data
public class MenuResourceSaveParamDTO {
    @ApiModelProperty(value = "资源名称", required = true, example = "订单列表")
    String resourceName;
    @ApiModelProperty(value = "操作名称", required = true, example = "添加")
    String optionName;
    @ApiModelProperty(value = "所属子菜单ID", required = true, example = "1")
    Long subMenuId;
    @ApiModelProperty(value = "路由", required = true, example = "route://")
    String route;
    @ApiModelProperty(value = "资源类型", required = true, example = "MAIN")
    AdminConstant.ResourceType type;
    @ApiModelProperty(value = "权限", required = true, example = "route://")
    String role;
    @ApiModelProperty(value = "菜单名称", required = true, example = "描述")
    String info;
    @ApiModelProperty(value = "条件表达式", example = "{id}==10 and {money}>=10")
    String condition;
    @ApiModelProperty(value = "按钮颜色", example = "#FFFFFF")
    String color;
}
