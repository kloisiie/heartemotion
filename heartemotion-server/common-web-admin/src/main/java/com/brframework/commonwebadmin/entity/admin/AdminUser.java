package com.brframework.commonwebadmin.entity.admin;

import com.alibaba.fastjson.annotation.JSONField;
import com.brframework.commonweb.json.BaseJson;
import com.brframework.commonwebadmin.service.admin.AdminRoleService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author xu
 * @date 2018/3/12 0012 下午 9:02
 * 后台用户
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "admin_user")
@ApiModel
public class AdminUser extends BaseJson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "id", required = true, example = "1")
    Integer id;

    @ApiModelProperty(value = "注册时间", required = true, example = "2018-03-12 21:32:33")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime createDate;

    /**
     * 登录名
     */
    @ApiModelProperty(value = "登录名", required = true, example = "name")
    String username;

    /**
     * 昵称
     */
    @ApiModelProperty(value = "昵称", example = "张三")
    String nickname;

    /**
     * 密码
     */
    @JSONField(serialize = false)
    String password;

    /**
     * 账号状态
     */
    @ApiModelProperty(value = "账号状态", example = "1")
    Integer state;

    /**
     * 用户拥有的角色权限
     */
    @ApiModelProperty(value = "拥有角色", required = true, example = "ADMIN,USER")
    @Column(length = Integer.MAX_VALUE)
    String roles;
}
