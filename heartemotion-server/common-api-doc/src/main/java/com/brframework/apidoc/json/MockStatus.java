package com.brframework.apidoc.json;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author xu
 * @date 2020/4/22 9:39
 */
@ApiModel
@Data
public class MockStatus {

    @ApiModelProperty(value = "MOCK功能是否开启", required = true, example = "1")
    private Boolean open;

}
