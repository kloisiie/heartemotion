package cn.bluetech.gragas.entity.hr;

import cn.bluetech.gragas.globals.Constant;
import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @Description 设备
 * @Author xu
 * @Date 2020/12/16 15:37:36
 */

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="hr_device")
@Builder
public class Device {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(value = "id", required = true, example = "1")
	private Long id;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "创建时间", required = true, example = "2018-03-12 21:32:33")
	private LocalDateTime createDate;
	@ApiModelProperty(value = "设备名称", required = true, example = "")
	private String deviceName;
	@ApiModelProperty(value = "设备标识", required = true, example = "")
	private String deviceId;
	@ApiModelProperty(value = "佩戴人", required = true, example = "")
	private String wearer;
	@ApiModelProperty(value = "是否开启报警", required = true, example = "")
	@Enumerated(EnumType.STRING)
	private Constant.Is police;
	@ApiModelProperty(value = "客户端id", required = true, example = "")
	private String clientId;

}