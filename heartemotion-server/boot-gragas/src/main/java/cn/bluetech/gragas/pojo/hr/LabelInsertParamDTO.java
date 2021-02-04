package cn.bluetech.gragas.pojo.hr;

import lombok.Data;
import java.time.LocalDateTime;
import cn.bluetech.gragas.globals.HeartRateConstant.LabelStatus;
import io.swagger.annotations.ApiModel;
import javax.persistence.Enumerated;
import io.swagger.annotations.ApiModelProperty;
import cn.bluetech.gragas.globals.HeartRateConstant.LabelType;


/**
 * 标注添加参数
 * @author xu
 * @date 2020-12-16 17:17:35
 */ 
@ApiModel
@Data
public class LabelInsertParamDTO{

    
    @ApiModelProperty(value = "标注时间", required = true, example = "")
    private LocalDateTime labelTime;
    
    @ApiModelProperty(value = "标注状态(无情绪、平稳、烦躁、高兴)", required = true, example = "")
    private LabelStatus labelStatus;
    
    @ApiModelProperty(value = "标注类型(手动标注、算法标注)", required = true, example = "")
    private LabelType labelType;




}