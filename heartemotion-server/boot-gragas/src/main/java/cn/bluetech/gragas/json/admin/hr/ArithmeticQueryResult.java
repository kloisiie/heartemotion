package cn.bluetech.gragas.json.admin.hr;

import java.lang.Long;

import com.brframework.commonweb.json.BaseJson;
import lombok.Data;
import com.alibaba.fastjson.annotation.JSONField;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import cn.bluetech.gragas.globals.HeartRateConstant.ArithmeticType;
import java.lang.String;
import io.swagger.annotations.ApiModelProperty;


/**
 * 算法查询结果
 * @author xu
 * @date 2020-12-16 16:37:39
 */ 
@ApiModel
@Data
public class ArithmeticQueryResult extends BaseJson {

    
    @ApiModelProperty(value = "id", required = true, example = "1")
    private Long id;
    
    @ApiModelProperty(value = "创建时间", required = true, example = "2018-03-12 21:32:33")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createDate;
    
    @ApiModelProperty(value = "算法名称", required = true, example = "算法A")
    private String name;

    @ApiModelProperty(value = "算法访问地址", required = true, example = "")
    private String serverUrl;
    
    @ApiModelProperty(value = "算法类型", required = true, example = "STANDARD")
    private ArithmeticType type;

    @ApiModelProperty(value = "算法类型", required = true, example = "STANDARD")
    private String typeName;


}