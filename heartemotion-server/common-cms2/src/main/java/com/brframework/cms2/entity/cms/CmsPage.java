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
 * @Description 页面
 * @Author xu
 * @Date 2020/11/07 16:05:05
 */

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="cms_page", indexes = {
		@Index(name = "IDX_ROUTE_NAME", columnList = "routeName", unique = true)
})
@Builder
public class CmsPage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(value = "id", required = true, example = "1")
	private Long id;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "创建时间", required = true, example = "2018-03-12 21:32:33")
	private LocalDateTime createDate;
	@ApiModelProperty(value = "组ID", required = true, example = "1")
	private Long groupId;
	@ApiModelProperty(value = "页面名称", required = true, example = "用户列表")
	private String name;
	@ApiModelProperty(value = "关联路由", required = true, example = "1")
	private String route;
	@ApiModelProperty(value = "路由名称", required = true, example = "1")
	@Column(length = 64)
	private String routeName;
	@ApiModelProperty(value = "页面内容", required = true, example = "1111")
	@Column(length = Integer.MAX_VALUE)
	private String content;
	@ApiModelProperty(value = "页面类型(静态路由、动态路由)", required = true, example = "STATIC")
	@Enumerated(value = EnumType.STRING)
	private CmsConstant.PageType type;
	@ApiModelProperty(value = "排序", required = true, example = "1")
	private Integer sort;

}