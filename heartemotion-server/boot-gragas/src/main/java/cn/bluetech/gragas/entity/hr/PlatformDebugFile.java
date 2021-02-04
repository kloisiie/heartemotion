package cn.bluetech.gragas.entity.hr;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @Description 平台调试文件
 * @Author xu
 * @Date 2020/12/16 21:41:16
 */

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="hr_platform_debug_file")
@Builder
public class PlatformDebugFile {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(value = "id", required = true, example = "1")
	private Long id;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "创建时间", required = true, example = "2018-03-12 21:32:33")
	private LocalDateTime createDate;
	@ApiModelProperty(value = "文件名", required = true, example = "abc.txt")
	private String fileName;
	@ApiModelProperty(value = "描述", required = true, example = "心率文件")
	private String details;
	@ApiModelProperty(value = "文件内容", required = true, example = "abcdfdsafsdf")
	@Column(length = Integer.MAX_VALUE)
	private String content;
	@ApiModelProperty(value = "数据开始时间", required = true, example = "2018-03-12 21:32:33")
	private LocalDateTime startTime;
	@ApiModelProperty(value = "数据结束时间", required = true, example = "2018-03-12 21:32:33")
	private LocalDateTime endTime;

}