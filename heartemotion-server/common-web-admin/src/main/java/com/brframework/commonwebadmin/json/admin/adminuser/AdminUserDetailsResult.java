package com.brframework.commonwebadmin.json.admin.adminuser;

import com.alibaba.fastjson.annotation.JSONField;
import com.brframework.commonweb.json.BaseJson;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author xu
 * @date 2021/1/4 11:12
 */
@Data
@ApiModel
public class AdminUserDetailsResult extends BaseJson {

    @ApiModelProperty(value = "ID", required = true, example = "1")
    Integer id;
    @ApiModelProperty(value = "注册时间", required = true, example = "2018-03-12 21:32:33")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime createDate;
    @ApiModelProperty(value = "登录名", required = true, example = "name")
    String username;
    @ApiModelProperty(value = "昵称", example = "张三")
    String nickname;
    @ApiModelProperty(value = "账号状态", example = "禁用")
    Integer state;
    @ApiModelProperty(value = "账号状态", example = "禁用")
    String stateName;
    @ApiModelProperty(value = "拥有角色", required = true, example = "ADMIN,USER")
    List<String[]> roles;

}
