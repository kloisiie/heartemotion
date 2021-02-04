package com.brframework.cms2.json.admin.cms;

import java.lang.Long;
import com.brframework.cms2.globals.CmsConstant.PageType;
import com.brframework.commonweb.json.BaseJson;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import javax.persistence.Enumerated;
import java.lang.String;
import javax.validation.constraints.NotNull;
import java.lang.Integer;
import io.swagger.annotations.ApiModelProperty;


/**
 * 页面添加参数
 * @author xu
 * @date 2020-11-07 17:59:48
 */ 
@ApiModel
@Data
public class CmsPageInsertParam extends BaseJson {

    
    @ApiModelProperty(value = "页面名称", required = true, example = "用户列表")
    @NotNull(message = "页面名称不能为空")
    private String name;
    
    @ApiModelProperty(value = "页面类型(静态路由、动态路由)", required = true, example = "STATIC")
    @NotNull(message = "页面类型(静态路由、动态路由)不能为空")
    private PageType type;
    
    @ApiModelProperty(value = "组ID", required = true, example = "1")
    @NotNull(message = "组ID不能为空")
    private Long groupId;
    
    @ApiModelProperty(value = "排序", required = true, example = "1")
    @NotNull(message = "排序不能为空")
    private Integer sort;
    
    @ApiModelProperty(value = "关联路由", required = true, example = "1")
    private String route;




}