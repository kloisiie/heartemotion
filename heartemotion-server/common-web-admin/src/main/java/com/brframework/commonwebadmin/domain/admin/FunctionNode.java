package com.brframework.commonwebadmin.domain.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author xu
 * @date 2018/3/12 0012 下午 7:39
 * 功能节点
 */
@Data
@ApiModel
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FunctionNode {

    @ApiModelProperty(value = "标题", required = true, example = "会员管理")
    String title;

    @ApiModelProperty(value = "链接", example = "/admin/link")
    String url;

    @ApiModelProperty(value = "功能所属角色", example = "ADMIN")
    String role;

    @ApiModelProperty(value = "子节点")
    List<FunctionNode> children;
}
