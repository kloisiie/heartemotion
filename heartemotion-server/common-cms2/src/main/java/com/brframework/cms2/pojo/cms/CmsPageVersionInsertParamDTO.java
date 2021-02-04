package com.brframework.cms2.pojo.cms;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import java.lang.String;
import java.lang.Long;
import io.swagger.annotations.ApiModelProperty;


/**
 * 页面版本添加参数
 * @author xu
 * @date 2020-11-07 18:37:22
 */ 
@ApiModel
@Data
public class CmsPageVersionInsertParamDTO{

    
    @ApiModelProperty(value = "名称", required = true, example = "首次创建")
    private String name;
    
    @ApiModelProperty(value = "创建人", required = true, example = "zhangsan")
    private String owner;
    
    @ApiModelProperty(value = "所属页面", required = true, example = "1")
    private Long pageId;
    
    @ApiModelProperty(value = "版本内容", required = true, example = "3232")
    private String content;




}