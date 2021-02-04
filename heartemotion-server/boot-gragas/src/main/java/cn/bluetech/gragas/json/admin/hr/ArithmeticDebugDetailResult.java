package cn.bluetech.gragas.json.admin.hr;

import cn.bluetech.gragas.globals.HeartRateConstant.DebugStatus;
import javax.persistence.GeneratedValue;
import java.lang.Long;

import com.brframework.commonweb.json.BaseJson;
import lombok.Data;
import com.alibaba.fastjson.annotation.JSONField;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import javax.persistence.Enumerated;
import io.swagger.annotations.ApiModelProperty;
import javax.persistence.Id;


/**
 * 算法调试详情结果
 * @author xu
 * @date 2020-12-16 16:50:52
 */ 
@ApiModel
@Data
public class ArithmeticDebugDetailResult extends BaseJson {

    
    @ApiModelProperty(value = "id", required = true, example = "1")
    private Long id;
    
    @ApiModelProperty(value = "创建时间", required = true, example = "2018-03-12 21:32:33")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createDate;
    
    @ApiModelProperty(value = "执行算法ID", required = true, example = "")
    private Long arithmeticId;
    
    @ApiModelProperty(value = "执行状态(进行中、成功、失败)", required = true, example = "")
    private DebugStatus status;




}