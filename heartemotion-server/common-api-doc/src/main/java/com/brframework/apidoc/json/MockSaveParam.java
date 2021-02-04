package com.brframework.apidoc.json;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author xu
 * @date 2020/4/20 15:08
 */
@ApiModel
@Data
public class MockSaveParam {
    @ApiModelProperty(value = "接口URL", required = true, example = "/api/doc.html")
    @NotEmpty
    private String apiUrl;
    @ApiModelProperty(value = "请求方法", required = true, example = "post")
    @NotEmpty
    private String httpMethod;
    @ApiModelProperty(value = "MOCK模板", required = true, example = "template")
    @NotEmpty
    private String template;
}
