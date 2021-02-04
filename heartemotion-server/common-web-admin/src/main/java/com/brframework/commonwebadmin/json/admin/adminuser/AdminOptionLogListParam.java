package com.brframework.commonwebadmin.json.admin.adminuser;

import com.brframework.commondb.annotation.param.ParamQuery;
import com.brframework.commondb.annotation.param.QueryExpression;
import com.brframework.commonweb.json.BaseJson;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 操作审计
 *
 * @author xu
 * @date 2019/10/9 15:03
 */
@Data
@ApiModel
public class AdminOptionLogListParam extends BaseJson {


    @ApiModelProperty(value = "状态", example = "OK")
    List<String> status;

    @ApiModelProperty(value = "登录名", example = "name")
    String username;

    @ApiModelProperty(value = "操作信息", example = "info")
    String info;

}
