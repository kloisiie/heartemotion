package com.brframework.cms2.json.admin.cms;

import com.alibaba.fastjson.annotation.JSONField;
import com.brframework.commonweb.json.BaseJson;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.Collections;
import java.util.List;

/**
 * @author xu
 * @date 2020/11/17 14:36
 */
@ApiModel
@Getter
public class CmsDictEntry extends BaseJson {

    @ApiModelProperty(value = "键", required = true, example = "1")
    private String key;

    @ApiModelProperty(value = "值", required = true, example = "role_add")
    private String value;

    @ApiModelProperty(value = "值", required = true, example = "role_add")
    private List<CmsDictEntry> children;

    public CmsDictEntry(String key, String value, List<CmsDictEntry> children){
        this.key = key;
        this.value = value;
        this.children = children;
    }

    public CmsDictEntry(String key, String value){
        this(key, value, Collections.emptyList());
    }

}
