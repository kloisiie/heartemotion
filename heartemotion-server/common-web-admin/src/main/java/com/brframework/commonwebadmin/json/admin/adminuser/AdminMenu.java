package com.brframework.commonwebadmin.json.admin.adminuser;

import com.alibaba.fastjson.annotation.JSONField;
import com.brframework.cms2.globals.CmsConstant;
import com.brframework.commonweb.json.BaseJson;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author xu
 * @date 2020/6/11 14:38
 */
@Data
@ApiModel
public class AdminMenu extends BaseJson {
    @ApiModelProperty(value = "名称", required = true, example = "用户管理")
    private String name;
    @ApiModelProperty(value = "图标", required = true, example = "icon")
    private String icon;
    @ApiModelProperty(value = "是否隐藏", required = true, example = "YES")
    private CmsConstant.Is hide;
    @ApiModelProperty(value = "类型", required = true, example = "LEFT")
    private CmsConstant.MenuType type;
    @ApiModelProperty(value = "子菜单列表", required = true)
    private List<AdminMenuSub> children;
}
