package com.brframework.cms2.generate.column;

import com.alibaba.fastjson.annotation.JSONField;
import com.brframework.cms2.generate.GenerateGlobals;
import com.brframework.cms2.generate.Option;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Map;

/**
 * 单选框
 * @author xu
 * @date 2020/12/8 11:55
 */
@SuperBuilder
@Getter
public class CheckboxColumnEntry extends ColumnEntry {

    /** 列类型 */
    @Builder.Default
    private GenerateGlobals.ColumnType type = GenerateGlobals.ColumnType.checkbox;

    /** 默认值 */
    @Builder.Default
    @JSONField(serialize = false)
    private List<Option> defaultValue = Lists.newArrayList();

    /** 数据列表 */
    @Builder.Default
    @JSONField(serialize = false)
    private List<Option> options = Lists.newArrayList();

    /** 动态加载 */
    @Builder.Default
    @JSONField(serialize = false)
    private boolean dynamic = false;

    /** 动态加载 */
    @JSONField(serialize = false)
    private String dynamicUrl;

    @JSONField(name = "config")
    @Override
    public Map<String, Object> getConfig(){
        Map<String, Object> config = super.getConfig();
        config.put("defaultValue", getDefaultValue());
        config.put("options", getOptions());
        config.put("dynamic", isDynamic());
        config.put("dynamicUrl", getDynamicUrl());
        Map<String, String> dynamicOptions = Maps.newHashMap();
        dynamicOptions.put("value", "value");
        dynamicOptions.put("label", "key");
        config.put("dynamicOptions", dynamicOptions);

        return config;
    }
}
