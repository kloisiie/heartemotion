package cn.bluetech.gragas.json.admin.hr;

import cn.bluetech.gragas.globals.HeartRateConstant;
import com.brframework.commonweb.json.BaseJson;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import java.lang.String;
import java.util.List;
import javax.validation.constraints.NotNull;
import io.swagger.annotations.ApiModelProperty;


/**
 * 算法添加参数
 * @author xu
 * @date 2020-12-16 16:37:39
 */ 
@ApiModel
@Data
public class ArithmeticInsertParam extends BaseJson {

    @ApiModelProperty(value = "算法类型(基准算法、普通算法)", required = true, example = "")
    @NotNull(message = "算法名称不能为空")
    private HeartRateConstant.ArithmeticType type;
    
    @ApiModelProperty(value = "算法名称", required = true, example = "")
    @NotNull(message = "算法名称不能为空")
    private String name;

    @ApiModelProperty(value = "算法访问地址", required = true, example = "")
    private String serverUrl;




}