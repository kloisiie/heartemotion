package com.brframework.commonwebadmin.entity.admin;

import com.alibaba.fastjson.annotation.JSONField;
import com.brframework.commonweb.json.BaseJson;
import com.brframework.commonwebadmin.service.admin.FunctionService;
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
 * 角色
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "admin_role")
@ApiModel
public class AdminRole extends BaseJson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "id", required = true, example = "1")
    Integer id;

    @ApiModelProperty(value = "创建时间", required = true, example = "2018-03-12 21:32:33")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime createDate;

    @ApiModelProperty(value = "角色名称", required = true, example = "管理员")
    String name;

    /**
     * 角色拥有的功能
     */
    @ApiModelProperty(value = "拥有权限", required = true, example = "ADMIN,USER")
    @Column(length = Integer.MAX_VALUE)
    String permissions;

}
