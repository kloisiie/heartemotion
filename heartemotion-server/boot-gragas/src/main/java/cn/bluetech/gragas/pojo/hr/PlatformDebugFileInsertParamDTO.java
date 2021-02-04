package cn.bluetech.gragas.pojo.hr;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import javax.persistence.Column;
import java.lang.String;
import io.swagger.annotations.ApiModelProperty;


/**
 * 平台调试文件添加参数
 * @author xu
 * @date 2020-12-16 21:51:26
 */ 
@ApiModel
@Data
public class PlatformDebugFileInsertParamDTO{

    
    @ApiModelProperty(value = "文件名", required = true, example = "abc.txt")
    private String fileName;
    
    @ApiModelProperty(value = "描述", required = true, example = "心率文件")
    private String details;
    
    @ApiModelProperty(value = "文件内容", required = true, example = "abcdfdsafsdf")
    private String content;




}