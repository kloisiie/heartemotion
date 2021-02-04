package com.brframework.commonwebadmin.json.admin.adminuser;

import com.brframework.commonweb.json.BaseJson;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

/**
 * @Author xu
 * @Date 2018/3/2 0002 下午 4:16
 * 登录返回
 */
@ApiModel
@Data
@Builder
public class LoginResult extends BaseJson {

    @ApiModelProperty(value = "令牌", required = true, example = "fmldkjflsdjflsdkfjsldkfjl=========")
    String token;

}
