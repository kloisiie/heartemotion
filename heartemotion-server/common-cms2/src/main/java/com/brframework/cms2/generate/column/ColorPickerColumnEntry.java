package com.brframework.cms2.generate.column;

import com.alibaba.fastjson.annotation.JSONField;
import com.brframework.cms2.generate.GenerateGlobals;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.Map;

/**
 * 颜色选择框
 * @author xu
 * @date 2020/12/8 11:53
 */
@SuperBuilder
@Getter
public class ColorPickerColumnEntry extends ColumnEntry {
    /** 列类型 */
    @Builder.Default
    private GenerateGlobals.ColumnType type = GenerateGlobals.ColumnType.color_picker;
    /** 透明 */
    @Builder.Default
    @JSONField(serialize = false)
    private boolean isOpacity = false;
    /** 默认值 */
    @Builder.Default
    @JSONField(serialize = false)
    private String defaultValue = "rgb(255,0,0)";

    @JSONField(name = "config")
    @Override
    public Map<String, Object> getConfig(){
        Map<String, Object> config = super.getConfig();
        config.put("isOpacity", isOpacity());
        config.put("defaultValue", getDefaultValue());

        return config;
    }
}
