package com.brframework.commonwebadmin.json.admin.adminuser;

import com.alibaba.fastjson.annotation.JSONField;
import com.brframework.cms2.globals.CmsConstant;
import com.brframework.commonweb.json.BaseJson;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author xu
 * @date 2020/6/12 18:24
 */
@Data
@ApiModel
public class AdminMenuSub extends BaseJson {
    @ApiModelProperty(value = "名称", required = true, example = "子菜单")
    private String name;
    @ApiModelProperty(value = "图标", required = true, example = "icon")
    private String icon;
    @ApiModelProperty(value = "页面路由", required = true, example = "route://menuManager --static")
    private String pageRoute;
    @ApiModelProperty(value = "权限标识", required = true, example = "role_add")
    private String role;
    @ApiModelProperty(value = "是否隐藏", required = true, example = "YES")
    private CmsConstant.Is hide;
    @ApiModelProperty(value = "子功能列表", required = true)
    private List<AdminMenuFun> children;
}
