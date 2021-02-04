package cn.bluetech.gragas.entity.hr;

import cn.bluetech.gragas.globals.HeartRateConstant;
import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @Description 算法
 * @Author xu
 * @Date 2020/12/16 15:37:36
 */

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="hr_arithmetic")
@Builder
public class Arithmetic {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(value = "id", required = true, example = "1")
	private Long id;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "创建时间", required = true, example = "2018-03-12 21:32:33")
	private LocalDateTime createDate;
	@ApiModelProperty(value = "算法名称", required = true, example = "")
	private String name;
	@ApiModelProperty(value = "算法访问地址", required = true, example = "")
	private String serverUrl;
	@ApiModelProperty(value = "算法类型(基准算法、普通算法)", required = true, example = "")
	@Enumerated(EnumType.STRING)
	private HeartRateConstant.ArithmeticType type;
	@ApiModelProperty(value = "排序", required = true, example = "")
	private Integer sort;
}