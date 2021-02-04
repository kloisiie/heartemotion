package cn.bluetech.gragas.json.api.hr;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author xu
 * @date 2020/12/21 15:55
 */
@ApiModel
@Data
public class HeartRateExportResult {

    /** 产生时间(秒) */
    private long hrTime;
    /** 心率值 */
    private int hrValue;
    /** 标注状态(0.无情绪、1.平稳、2.烦躁、3.高兴) */
    private int labelStatus;
    /** 应对手段 */
    private String means;
    /** 标注类型(0.手动标注、1.算法标注) */
    private int labelType;

}
