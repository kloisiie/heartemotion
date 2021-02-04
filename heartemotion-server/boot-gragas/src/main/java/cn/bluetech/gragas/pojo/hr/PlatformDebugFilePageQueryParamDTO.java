package cn.bluetech.gragas.pojo.hr;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import java.lang.String;
import io.swagger.annotations.ApiModelProperty;


/**
 * 平台调试文件分页参数
 * @author xu
 * @date 2020-12-16 21:51:26
 */ 
@ApiModel
@Data
public class PlatformDebugFilePageQueryParamDTO{

    
    @ApiModelProperty(value = "文件名", required = true, example = "abc.txt")
    private String fileName;




}