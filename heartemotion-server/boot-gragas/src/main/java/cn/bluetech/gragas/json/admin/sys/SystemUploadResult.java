package cn.bluetech.gragas.json.admin.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author xu
 * @date 2020/12/24 15:55
 */
@ApiModel
@Data
public class SystemUploadResult {

    @ApiModelProperty(value = "访问地址")
    private String url;

}
