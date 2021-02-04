package com.brframework.cms2.json.admin.cms;

import com.alibaba.fastjson.annotation.JSONField;
import com.brframework.cms2.globals.CmsConstant;
import com.brframework.commonweb.json.BaseJson;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;


/**
 * 子菜单查询结果
 * @author xu
 * @date 2020-11-07 16:37:38
 */ 
@ApiModel
@Data
public class CmsMenuSubQueryResult extends BaseJson {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "id", required = true, example = "1")
    private Long id;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间", required = true, example = "2018-03-12 21:32:33")
    private LocalDateTime createDate;
    @ApiModelProperty(value = "名称", required = true, example = "子菜单")
    private String name;
    @ApiModelProperty(value = "主菜单ID", required = true, example = "1")
    private Long menuId;
    @ApiModelProperty(value = "图标", required = true, example = "icon")
    private String icon;
    @ApiModelProperty(value = "排序", required = true, example = "1")
    private Integer sort;
    @ApiModelProperty(value = "路由页面", required = true, example = "1")
    private Long[] pageId;
    @ApiModelProperty(value = "路由名称", required = true, example = "1")
    private String pageName;
    @ApiModelProperty(value = "权限标识", required = true, example = "role_add")
    private String role;
    @ApiModelProperty(value = "是否隐藏", required = true, example = "YES")
    private CmsConstant.Is hide;
    @ApiModelProperty(value = "菜单功能列表", required = true)
    private List<CmsMenuFunQueryResult> children;
    @ApiModelProperty(value = "级别", required = true)
    private Integer level = 2;
}