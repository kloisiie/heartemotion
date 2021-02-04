package cn.bluetech.gragas.json.admin.hr;

import javax.persistence.GeneratedValue;
import java.lang.Long;

import com.brframework.commonweb.json.BaseJson;
import lombok.Data;
import com.alibaba.fastjson.annotation.JSONField;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import javax.persistence.Enumerated;
import cn.bluetech.gragas.globals.HeartRateConstant.ArithmeticType;
import java.lang.String;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import javax.persistence.Id;


/**
 * 算法详情结果
 * @author xu
 * @date 2020-12-16 16:37:39
 */ 
@ApiModel
@Data
public class ArithmeticDetailResult extends BaseJson {

    
    @ApiModelProperty(value = "id", required = true, example = "1")
    private Long id;
    
    @ApiModelProperty(value = "创建时间", required = true, example = "2018-03-12 21:32:33")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createDate;
    
    @ApiModelProperty(value = "算法名称", required = true, example = "")
    private String name;

    @ApiModelProperty(value = "算法访问地址", required = true, example = "")
    private String serverUrl;
    
    @ApiModelProperty(value = "算法类型(基准算法、普通算法)", required = true, example = "")
    private ArithmeticType type;




}