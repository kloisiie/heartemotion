package com.brframework.commonwebadmin.domain.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

/**
 * @author xu
 * @date 2018/3/29 0029 下午 2:07
 */
@Builder
@ApiModel
@Data
public class FunctionRole {

    @ApiModelProperty(value = "权限标识", required = true, example = "ADMIN")
    String role;
    @ApiModelProperty(value = "权限标题", required = true, example = "管理员")
    String title;

}
