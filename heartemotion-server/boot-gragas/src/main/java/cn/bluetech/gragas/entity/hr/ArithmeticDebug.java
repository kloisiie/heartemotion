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
 * @Description 算法调试
 * @Author xu
 * @Date 2020/12/16 15:37:36
 */

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="hr_arithmetic_debug")
@Builder
public class ArithmeticDebug {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(value = "id", required = true, example = "1")
	private Long id;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "创建时间", required = true, example = "2018-03-12 21:32:33")
	private LocalDateTime createDate;
	@ApiModelProperty(value = "基准执行结果", required = true, example = "")
	@Column(length = Integer.MAX_VALUE)
	private String standardResult;
	@ApiModelProperty(value = "执行算法ID", required = true, example = "")
	private Long arithmeticId;
	@ApiModelProperty(value = "调试算法执行结果", required = true, example = "")
	@Column(length = Integer.MAX_VALUE)
	private String debugResult;
	@ApiModelProperty(value = "执行状态(进行中、成功、失败)", required = true, example = "")
	@Enumerated(EnumType.STRING)
	private HeartRateConstant.DebugStatus status;
	@ApiModelProperty(value = "客户端ID", required = true, example = "")
	private String clientId;

}