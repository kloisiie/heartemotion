package com.brframework.cms2.json.admin.cms;

import com.brframework.commonweb.json.BaseJson;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import java.lang.String;
import javax.validation.constraints.NotNull;
import java.lang.Integer;
import io.swagger.annotations.ApiModelProperty;


/**
 * 页面组修改参数
 * @author xu
 * @date 2020-11-07 18:01:00
 */ 
@ApiModel
@Data
public class CmsPageGroupUpdateParam extends BaseJson {

    
    @ApiModelProperty(value = "组名称", required = true, example = "用户管理")
    @NotNull(message = "组名称不能为空")
    private String name;
    
    @ApiModelProperty(value = "排序", required = true, example = "1")
    @NotNull(message = "排序不能为空")
    private Integer sort;




}