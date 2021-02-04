package com.brframework.apidoc.json;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author xu
 * @date 2020/4/20 15:07
 */
@ApiModel
@Data
public class MockParam {

    @ApiModelProperty(value = "接口URL", required = true, example = "/api/doc.html")
    private String apiUrl;
    @ApiModelProperty(value = "请求方法", required = true, example = "post")
    private String httpMethod;
}
