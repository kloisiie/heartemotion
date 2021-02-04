package com.brframework.cms2.entity.cms;

import com.alibaba.fastjson.annotation.JSONField;
import com.brframework.cms2.globals.CmsConstant;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @Description 子菜单
 * @Author xu
 * @Date 2020/11/07 16:05:05
 */

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="cms_menu_sub")
@Builder
public class CmsMenuSub {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(value = "id", required = true, example = "1")
	private Long id;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "创建时间", required = true, example = "2018-03-12 21:32:33")
	private LocalDateTime createDate;
	@ApiModelProperty(value = "名称", required = true, example = "子菜单")
	private String name;
	@ApiModelProperty(value = "主菜单ID", required = true, example = "1")
	private Long menuId;
	@ApiModelProperty(value = "图标", required = true, example = "icon")
	private String icon;
	@ApiModelProperty(value = "排序", required = true, example = "1")
	private Integer sort;
	@ApiModelProperty(value = "路由页面", required = true, example = "1")
	private Long pageId;
	@ApiModelProperty(value = "权限标识", required = true, example = "role_add")
	private String role;
	@ApiModelProperty(value = "是否隐藏", required = true, example = "YES")
	@Enumerated(value = EnumType.STRING)
	private CmsConstant.Is hide;

}