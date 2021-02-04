package com.brframework.commonwebadmin.json.admin.adminuser;

import com.brframework.commonweb.json.BaseJson;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.List;

/**
 * @author xu
 * @date 2018/3/13 0013 下午 5:34
 */
@Data
@ApiModel
public class AdminUserUpdateParam extends BaseJson {

    /**
     * 昵称
     */
    @ApiModelProperty(value = "昵称", example = "张三")
    @Length(min = 1, max = 18, message = "昵称长度在1-18之间")
    String nickname;

    /**
     * 用户拥有的角色权限
     */
    @ApiModelProperty(value = "用户角色", required = true, example = "ADMIN,USER")
    List<String[]> roles;

}
