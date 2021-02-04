package com.brframework.cms2.generate.column;

import com.alibaba.fastjson.annotation.JSONField;
import com.brframework.cms2.generate.GenerateGlobals;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.Map;

/**
 * 数字输入框
 * @author xu
 * @date 2020/12/8 11:54
 */
@SuperBuilder
@Getter
public class NumberColumnEntry extends ColumnEntry {
    /** 列类型 */
    @Builder.Default
    private GenerateGlobals.ColumnType type = GenerateGlobals.ColumnType.number;
    /** 默认值 */
    @Builder.Default
    @JSONField(serialize = false)
    private String defaultValue = "";

    /** 最大 */
    @Builder.Default
    @JSONField(serialize = false)
    private Integer max = 50;

    /** 最小 */
    @Builder.Default
    @JSONField(serialize = false)
    private Integer min = 1;

    /** 占位内容 */
    @Builder.Default
    @JSONField(serialize = false)
    private String placeholder = "";

    /** 步长 */
    @Builder.Default
    @JSONField(serialize = false)
    private Integer step = 1;

    /** 精度 */
    @Builder.Default
    @JSONField(serialize = false)
    private Integer precision = 0;

    @JSONField(name = "config")
    @Override
    public Map<String, Object> getConfig(){
        Map<String, Object> config = super.getConfig();
        config.put("defaultValue", getDefaultValue());
        config.put("max", getMax());
        config.put("min", getMin());
        config.put("placeholder", getPlaceholder());
        config.put("step", getStep());


        return config;
    }
}
