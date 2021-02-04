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
 * 菜单查询结果
 * @author xu
 * @date 2020-11-07 16:34:16
 */ 
@ApiModel
@Data
public class CmsMenuQueryResult extends BaseJson {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "id", required = true, example = "1")
    private Long id;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间", required = true, example = "2018-03-12 21:32:33")
    private LocalDateTime createDate;
    @ApiModelProperty(value = "名称", required = true, example = "用户管理")
    private String name;
    @ApiModelProperty(value = "图标", required = true, example = "icon")
    private String icon;
    @ApiModelProperty(value = "排序", required = true, example = "1")
    private Integer sort;
    @ApiModelProperty(value = "类型", required = true, example = "LEFT")
    private CmsConstant.MenuType type;
    @ApiModelProperty(value = "是否隐藏", required = true, example = "YES")
    private CmsConstant.Is hide;
    @ApiModelProperty(value = "子菜单列表", required = true)
    private List<CmsMenuSubQueryResult> children;
    @ApiModelProperty(value = "级别", required = true)
    private Integer level = 1;

}