package com.brframework.cms2.generate.column;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.annotation.JSONField;
import com.brframework.cms2.generate.GenerateGlobals;
import com.google.common.collect.Maps;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.Map;

/**
 * 列
 * @author xu
 * @date 2020/12/8 11:53
 */
@SuperBuilder
@Getter
public class ColumnEntry {

    /** 列类型 */
    private GenerateGlobals.ColumnType type;
    /** 列标签 */
    @JSONField(serialize = false)
    private String label;
    /** 列标识 */
    @JSONField(serialize = false)
    private String prop;
    /** 是否必须 */
    @Builder.Default
    @JSONField(serialize = false)
    private boolean required = false;
    /** 是否禁用 */
    @Builder.Default
    @JSONField(serialize = false)
    private boolean disabled = false;

    @Builder.Default
    private String belong = "";

    @Builder.Default
    private String key = IdUtil.simpleUUID();


    @JSONField(name = "config")
    public Map<String, Object> getConfig(){
        Map<String, Object> config = Maps.newHashMap();
        config.put("label", getLabel());
        config.put("prop", getProp());
        config.put("required", isRequired());
        config.put("disabled", isDisabled());

        return config;
    }

}
