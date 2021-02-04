package com.brframework.commonwebadmin.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author xu
 * @date 2020/6/12 12:55
 */
@Data
public class MenuMasterSaveParamDTO {
    @ApiModelProperty(value = "菜单名称", required = true, example = "菜单名称")
    String menuName;
    @ApiModelProperty(value = "图标", required = true, example = "icon")
    String icon;
    @ApiModelProperty(value = "排序", required = true, example = "1")
    Integer sort;
    @ApiModelProperty(value = "子菜单", required = true, example = "1")
    String subMenuIds;
}
