package cn.bluetech.gragas.pojo.hr;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import javax.persistence.Column;
import java.lang.String;
import io.swagger.annotations.ApiModelProperty;


/**
 * 用户调试文件添加参数
 * @author xu
 * @date 2020-12-16 21:47:34
 */ 
@ApiModel
@Data
public class ClientDebugFileInsertParamDTO{

    
    @ApiModelProperty(value = "文件名", required = true, example = "abc.txt")
    private String fileName;
    
    @ApiModelProperty(value = "文件内容", required = true, example = "abcdfdsafsdf")
    private String content;
    
    @ApiModelProperty(value = "客户端ID", required = true, example = "sdfsdfsdfsdf")
    private String clientId;


}