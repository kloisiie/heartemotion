package com.brframework.commonwebadmin.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import java.util.List;

/**
 * @author xu
 * @date 2020/6/12 12:55
 */
@Data
public class MenuSubSaveParamDTO {
    @ApiModelProperty(value = "归类", required = true, example = "用户")
    String classify;
    @ApiModelProperty(value = "菜单名称", required = true, example = "菜单名称")
    String menuName;
    @ApiModelProperty(value = "图标", required = true, example = "icon")
    String icon;
    @ApiModelProperty(value = "排序", required = true, example = "1")
    Integer sort;
    @ApiModelProperty(value = "入口路由", required = true, example = "1")
    String mainRoute;
    @ApiModelProperty(value = "入口权限", required = true, example = "1")
    String mainRole;
}
