package com.brframework.cms2.json.admin.cms;

import com.alibaba.fastjson.annotation.JSONField;
import com.brframework.commonweb.json.BaseJson;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;


/**
 * 菜单功能查询结果
 * @author xu
 * @date 2020-11-07 16:39:04
 */ 
@ApiModel
@Data
public class CmsMenuFunQueryResult extends BaseJson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "id", required = true, example = "1")
    private Long id;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间", required = true, example = "2018-03-12 21:32:33")
    private LocalDateTime createDate;
    @ApiModelProperty(value = "名称", required = true, example = "功能")
    private String name;
    @ApiModelProperty(value = "子菜单ID", required = true, example = "1")
    private Long menuSubId;
    @ApiModelProperty(value = "排序", required = true, example = "1")
    private Integer sort;
    @ApiModelProperty(value = "权限标识", required = true, example = "role_add")
    private String role;
    @ApiModelProperty(value = "级别", required = true)
    private Integer level = 3;


}