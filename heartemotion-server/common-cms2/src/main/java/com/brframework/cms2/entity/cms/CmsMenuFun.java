package com.brframework.cms2.entity.cms;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @Description 菜单功能
 * @Author xu
 * @Date 2020/11/07 16:05:05
 */

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="cms_menu_fun")
@Builder
public class CmsMenuFun {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(value = "id", required = true, example = "1")
	private Long id;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "创建时间", required = true, example = "2018-03-12 21:32:33")
	private LocalDateTime createDate;
	@ApiModelProperty(value = "名称", required = true, example = "功能")
	private String name;
	@ApiModelProperty(value = "子菜单ID", required = true, example = "1")
	private Long menuSubId;
	@ApiModelProperty(value = "排序", required = true, example = "1")
	private Integer sort;
	@ApiModelProperty(value = "权限标识", required = true, example = "role_add")
	private String role;

}