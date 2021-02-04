package cn.bluetech.gragas.entity.client;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @Description 操作日志
 * @Author xu
 * @Date 2020/12/16 15:37:36
 */

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="client_option_log")
@Builder
public class OptionLog {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(value = "id", required = true, example = "1")
	private Long id;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "创建时间", required = true, example = "2018-03-12 21:32:33")
	private LocalDateTime createDate;
	@ApiModelProperty(value = "客户端ID", required = true, example = "")
	private String clientId;
	@ApiModelProperty(value = "调用接口", required = true, example = "")
	private String callApi;
	@ApiModelProperty(value = "IP", required = true, example = "")
	private String ip;

}