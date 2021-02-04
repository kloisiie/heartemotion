package cn.bluetech.gragas.json.api.hr;

import cn.bluetech.gragas.globals.Constant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author xu
 * @date 2020/12/17 11:08
 */
@ApiModel
@Data
public class LabelAddResult {

    @ApiModelProperty(value = "是否已经存在", required = true, example = "YES")
    private Constant.Is exist;

}
