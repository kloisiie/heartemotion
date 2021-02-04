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
 * @Description 版本
 * @Author xu
 * @Date 2020/11/07 16:05:05
 */

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="cms_version")
@Builder
public class CmsPageVersion {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(value = "id", required = true, example = "1")
	private Long id;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "创建时间", required = true, example = "2018-03-12 21:32:33")
	private LocalDateTime createDate;
	@ApiModelProperty(value = "名称", required = true, example = "首次创建")
	private String name;
	@ApiModelProperty(value = "所属页面", required = true, example = "1")
	private Long pageId;
	@ApiModelProperty(value = "创建人", required = true, example = "zhangsan")
	private String owner;
	@ApiModelProperty(value = "版本号", required = true, example = "232323")
	private String versionName;
	@ApiModelProperty(value = "版本内容", required = true, example = "3232")
	@Column(length = Integer.MAX_VALUE)
	private String content;
	@ApiModelProperty(value = "是否删除", required = true, example = "NORMAL")
	@Enumerated(value = EnumType.STRING)
	private CmsConstant.Delete isDelete;

}