package com.brframework.commonwebadmin.entity.admin;

import com.alibaba.fastjson.annotation.JSONField;
import com.brframework.commonweb.json.BaseJson;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 管理员操作日志
 *
 * @author xu
 * @date 2019/10/9 13:53
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "admin_option_log")
@Builder
@ApiModel
public class AdminOptionLog extends BaseJson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "id", required = true, example = "1")
    Long id;

    @ApiModelProperty(value = "操作时间", required = true, example = "2018-03-12 21:32:33")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime createDate;

    @ApiModelProperty(value = "操作者id", required = true, example = "12")
//    @HideColumn
    Integer userId;

    @ApiModelProperty(value = "操作者登录名", required = true, example = "admin")
//    @TextColumn(uriMappingMethod = "adminUserRoleController.userInfoByUserId")
    String username;

    @ApiModelProperty(value = "操作信息", required = true, example = "登录")
    String ip;

    @ApiModelProperty(value = "操作状态", required = true, example = "OK")
    String status;

    @ApiModelProperty(value = "操作信息", required = true, example = "登录")
    String info;

    @ApiModelProperty(value = "操作触发api", required = true, example = "/api/v1/get")
    String callApi;

    @ApiModelProperty(value = "携带参数", required = true, example = "{}")
    @Column(length = Integer.MAX_VALUE)
//    @TextAreaColumn
    String param;

    @ApiModelProperty(value = "错误日志", required = true, example = "info")
    @Column(length = Integer.MAX_VALUE)
//    @TextAreaColumn
    String errorLog;


}
