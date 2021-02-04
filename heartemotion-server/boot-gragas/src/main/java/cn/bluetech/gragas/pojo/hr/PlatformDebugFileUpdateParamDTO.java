package cn.bluetech.gragas.pojo.hr;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;


/**
 * 平台调试文件更新参数
 * @author xu
 * @date 2020-12-16 21:51:26
 */ 
@ApiModel
@Data
public class PlatformDebugFileUpdateParamDTO{

    @ApiModelProperty(value = "文件名", required = true, example = "abc.txt")
    @NotNull(message = "文件名不能为空")
    private String fileName;

    @ApiModelProperty(value = "描述", required = true, example = "心率文件")
    @NotNull(message = "描述不能为空")
    private String details;

    @ApiModelProperty(value = "文件内容", required = true, example = "abcdfdsafsdf")
    @NotNull(message = "文件内容不能为空")
    private String content;




}