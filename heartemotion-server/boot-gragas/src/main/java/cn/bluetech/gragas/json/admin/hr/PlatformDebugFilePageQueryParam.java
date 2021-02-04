package cn.bluetech.gragas.json.admin.hr;

import com.brframework.commonweb.json.BaseJson;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import java.lang.String;
import io.swagger.annotations.ApiModelProperty;


/**
 * 平台调试文件查询参数
 * @author xu
 * @date 2020-12-16 21:51:27
 */ 
@ApiModel
@Data
public class PlatformDebugFilePageQueryParam extends BaseJson {

    
    @ApiModelProperty(value = "文件名", required = true, example = "abc.txt")
    private String fileName;




}