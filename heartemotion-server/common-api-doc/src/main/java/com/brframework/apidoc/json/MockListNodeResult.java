package com.brframework.apidoc.json;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;

/**
 * @author xu
 * @date 2020/6/3 21:32
 */
@ApiModel
@Data
public class MockListNodeResult {
    @ApiModelProperty(value = "接口URL", required = true, example = "/api/doc.html")
    private String apiUrl;
    @ApiModelProperty(value = "请求方法", required = true, example = "post")
    private String httpMethod;
    @ApiModelProperty(value = "MOCK模板", required = true, example = "template")
    @Column(length = Integer.MAX_VALUE)
    private String template;
}
