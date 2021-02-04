package com.brframework.commonwebadmin.json.admin.adminuser;

import com.brframework.commonweb.json.BaseJson;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * @author xu
 * @date 2018/3/13 0013 下午 5:47
 * 修改密码
 */
@Data
@ApiModel
public class AdminUserPasswordParam extends BaseJson {


    /** 密码 */
    @Length(min = 1, max = 18, message = "密码长度在1-18之间")
    @ApiModelProperty(value = "密码", required = true, example = "1")
    String password;

}
