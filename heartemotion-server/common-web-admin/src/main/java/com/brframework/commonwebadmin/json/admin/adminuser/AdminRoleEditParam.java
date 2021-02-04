package com.brframework.commonwebadmin.json.admin.adminuser;

import com.brframework.commonweb.json.BaseJson;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author xu
 * @date 2019/11/11 11:35
 */
@Data
@ApiModel
public class AdminRoleEditParam extends BaseJson {

    @ApiModelProperty(value = "角色名称", required = true, example = "管理员")
    String name;

    /**
     * 角色拥有的功能
     */
    @ApiModelProperty(value = "拥有权限", required = true, example = "ADMIN,USER")
//    @TreeColumn(optionsMappingContext = FunctionService.SELECT_PERMISSION)
    String permissions;

}
