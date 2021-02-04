package com.brframework.commonwebadmin.json.admin.adminuser;

import com.brframework.commondb.annotation.param.ParamQuery;
import com.brframework.commondb.annotation.param.QueryExpression;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author xu
 * @date 2020/12/15 11:51
 */
@Data
public class AdminOptionLogListQueryParam {

    @ParamQuery(expression = QueryExpression.eq)
    String status;

    @ParamQuery(expression = QueryExpression.like)
    String username;

    @ParamQuery(expression = QueryExpression.like)
    String info;

}
