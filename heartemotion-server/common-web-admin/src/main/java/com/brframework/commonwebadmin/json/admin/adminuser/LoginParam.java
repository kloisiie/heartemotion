package com.brframework.commonwebadmin.json.admin.adminuser;

import com.brframework.commonweb.json.BaseJson;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * @Author xu
 * @Date 2018/3/2 0002 上午 10:02
 * 登录信息
 */
@ApiModel
@Data
public class LoginParam extends BaseJson {

    @ApiModelProperty(value = "登录名", required = true, example = "admin")
    @Length(max = 32, message = "登录名最长32个字符")
    String username;

    @ApiModelProperty(value = "密码", required = true, example = "123456")
    String password;

}
