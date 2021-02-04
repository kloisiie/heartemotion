package cn.bluetech.gragas.json.api.hr;

import cn.bluetech.gragas.json.admin.hr.PoliceQueryResult;
import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author xu
 * @date 2020/12/21 14:27
 */
@ApiModel
@Data
public class PolicePollResult {

    @ApiModelProperty(value = "报警列表", required = true)
    private List<PoliceQueryResult> polices;

    @ApiModelProperty(value = "时间标记", required = true, example = "2018-03-12 21:32:33")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timeMark;

}
