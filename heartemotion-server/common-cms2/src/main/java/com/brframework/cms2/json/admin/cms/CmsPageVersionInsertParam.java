package com.brframework.cms2.json.admin.cms;

import com.brframework.commonweb.json.BaseJson;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import java.lang.String;
import javax.validation.constraints.NotNull;
import java.lang.Long;
import io.swagger.annotations.ApiModelProperty;


/**
 * 页面版本添加参数
 * @author xu
 * @date 2020-11-07 18:37:22
 */ 
@ApiModel
@Data
public class CmsPageVersionInsertParam extends BaseJson {

    
    @ApiModelProperty(value = "名称", required = true, example = "首次创建")
    @NotNull(message = "名称不能为空")
    private String name;
    
    @ApiModelProperty(value = "所属页面", required = true, example = "1")
    @NotNull(message = "所属页面不能为空")
    private Long pageId;
    
    @ApiModelProperty(value = "版本内容", required = true, example = "3232")
    @NotNull(message = "版本内容不能为空")
    private String content;




}