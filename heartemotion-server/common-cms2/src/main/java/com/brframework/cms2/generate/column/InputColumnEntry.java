package com.brframework.cms2.generate.column;

import com.alibaba.fastjson.annotation.JSONField;
import com.brframework.cms2.generate.GenerateGlobals;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.Map;

/**
 * 输入框
 * @author xu
 * @date 2020/12/8 11:53
 */
@SuperBuilder
@Getter
public class InputColumnEntry extends ColumnEntry {
    /** 列类型 */
    @Builder.Default
    private GenerateGlobals.ColumnType type = GenerateGlobals.ColumnType.input;
    /** 占位内容 */
    @Builder.Default
    @JSONField(serialize = false)
    private String placeholder = "";
    /** 正则 */
    @Builder.Default
    @JSONField(serialize = false)
    private String[] pattern = new String[]{};
    /** 最短 */
    @Builder.Default
    @JSONField(serialize = false)
    private int minLength = 1;
    /** 最长 */
    @Builder.Default
    @JSONField(serialize = false)
    private int maxLength = 50;
    /** 文本性 */
    @Builder.Default
    @JSONField(serialize = false)
    private boolean textual = false;
    /** 默认值 */
    @Builder.Default
    @JSONField(serialize = false)
    private String defaultValue = "";

    @JSONField(name = "config")
    @Override
    public Map<String, Object> getConfig(){
        Map<String, Object> config = super.getConfig();
        config.put("placeholder", getPlaceholder());
        config.put("pattern", getPattern());
        config.put("minLength", getMinLength());
        config.put("maxLength", getMaxLength());
        config.put("textual", isTextual());
        config.put("defaultValue", getDefaultValue());

        return config;
    }
}
