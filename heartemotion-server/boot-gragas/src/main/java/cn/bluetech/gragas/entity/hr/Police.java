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
 * @Description 报警记录
 * @Author xu
 * @Date 2020/12/16 15:37:36
 */

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="hr_police", indexes = {
		@Index(name = "IDX_POLICE_TIME", columnList = "policeTime"),
		@Index(name = "IDX_DEVICE_ID", columnList = "deviceId"),
})
@Builder
public class Police {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(value = "id", required = true, example = "1")
	private Long id;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "创建时间", required = true, example = "2018-03-12 21:32:33")
	private LocalDateTime createDate;
	@ApiModelProperty(value = "报警时间", required = true, example = "")
	private LocalDateTime policeTime;
	@ApiModelProperty(value = "设备标识", required = true, example = "")
	private String deviceId;

}