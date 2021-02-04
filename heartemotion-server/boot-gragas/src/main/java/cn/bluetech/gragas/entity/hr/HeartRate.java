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
 * @Description 心率
 * @Author xu
 * @Date 2020/12/16 15:37:36
 */

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="hr_heart_rate", indexes = {
		@Index(name = "IDX_HR_TIME", columnList = "hrTime"),
		@Index(name = "IDX_DEVICE_ID", columnList = "deviceId"),
		@Index(name = "IDX_WEARER", columnList = "wearer"),
})
@Builder
public class HeartRate {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(value = "id", required = true, example = "1")
	private Long id;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "创建时间", required = true, example = "2018-03-12 21:32:33")
	private LocalDateTime createDate;
	@ApiModelProperty(value = "产生时间", required = true, example = "")
	private LocalDateTime hrTime;
	@ApiModelProperty(value = "设备标识", required = true, example = "")
	private String deviceId;
	@ApiModelProperty(value = "心率值", required = true, example = "")
	private Integer hrValue;
	@ApiModelProperty(value = "佩戴人", required = true, example = "")
	private String wearer;
	@ApiModelProperty(value = "标注状态(无情绪、平稳、烦躁、高兴)", required = true, example = "")
	@Enumerated(value = EnumType.STRING)
	private HeartRateConstant.LabelStatus labelStatus;
	@ApiModelProperty(value = "应对手段", required = true, example = "各种方法")
	private String means;
	@ApiModelProperty(value = "标注类型(手动标注、算法标注)", required = true, example = "")
	@Enumerated(value = EnumType.STRING)
	private HeartRateConstant.LabelType labelType;

}