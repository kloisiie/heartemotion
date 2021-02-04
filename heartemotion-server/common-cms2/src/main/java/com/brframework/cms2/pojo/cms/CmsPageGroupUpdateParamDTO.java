package com.brframework.cms2.pojo.cms;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import java.lang.String;
import java.lang.Integer;
import io.swagger.annotations.ApiModelProperty;


/**
 * 页面组更新参数
 * @author xu
 * @date 2020-11-07 18:01:00
 */ 
@ApiModel
@Data
public class CmsPageGroupUpdateParamDTO{

    
    @ApiModelProperty(value = "组名称", required = true, example = "用户管理")
    private String name;
    
    @ApiModelProperty(value = "排序", required = true, example = "1")
    private Integer sort;




}