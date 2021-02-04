package com.brframework.commonwebadmin.json.admin.adminuser;

import com.brframework.commonweb.json.BaseJson;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.List;

/**
 * @author xu
 * @date 2018/3/13 0013 下午 5:35
 */
@Data
@ApiModel
public class AdminUserCreateParam extends BaseJson {

    /**
     * 登录名
     */
    @ApiModelProperty(value = "登录名", required = true, example = "name")
    @Length(min = 1, max = 18, message = "登录名长度在1-18之间")
    String username;

    /**
     * 昵称
     */
    @ApiModelProperty(value = "昵称", example = "张三")
    @Length(min = 1, max = 18, message = "昵称长度在1-18之间")
    String nickname;

    /**
     * 密码
     */
    @Length(min = 1, max = 18, message = "密码长度在1-18之间")
    @ApiModelProperty(value = "密码", example = "123456")
    String password;

    /**
     * 用户角色
     */
    @ApiModelProperty(value = "用户角色", required = true, example = "ADMIN,USER")
    List<String[]> roles;
}
