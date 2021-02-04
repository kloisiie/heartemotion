package cn.bluetech.gragas.json.admin.hr;

import com.brframework.commonweb.json.BaseJson;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import javax.persistence.Column;
import java.lang.String;
import javax.validation.constraints.NotNull;
import io.swagger.annotations.ApiModelProperty;


/**
 * 用户调试文件添加参数
 * @author xu
 * @date 2020-12-16 21:47:34
 */ 
@ApiModel
@Data
public class ClientDebugFileInsertParam extends BaseJson {

    
    @ApiModelProperty(value = "文件名", required = true, example = "abc.txt")
    @NotNull(message = "文件名不能为空")
    private String fileName;
    
    @ApiModelProperty(value = "文件内容", required = true, example = "abcdfdsafsdf")
    @NotNull(message = "文件内容不能为空")
    private String content;




}