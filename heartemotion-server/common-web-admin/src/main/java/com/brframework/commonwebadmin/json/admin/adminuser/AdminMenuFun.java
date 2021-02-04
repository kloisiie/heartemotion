package com.brframework.commonwebadmin.json.admin.adminuser;

import com.alibaba.fastjson.annotation.JSONField;
import com.brframework.commonweb.json.BaseJson;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

/**
 * @author xu
 * @date 2020/11/16 18:08
 */
@ApiModel
@Data
public class AdminMenuFun extends BaseJson {
    @ApiModelProperty(value = "名称", required = true, example = "功能")
    private String name;
    @ApiModelProperty(value = "权限标识", required = true, example = "role_add")
    private String role;

}
