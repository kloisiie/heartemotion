package com.brframework.cms2.generate.column;

import com.alibaba.fastjson.annotation.JSONField;
import com.brframework.cms2.generate.GenerateGlobals;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.Map;

/**
 * 开关
 * @author xu
 * @date 2020/12/8 11:53
 */
@SuperBuilder
@Getter
public class SwitchColumnEntry extends ColumnEntry {
    /** 列类型 */
    @Builder.Default
    @JSONField(serialize = false)
    private GenerateGlobals.ColumnType type = GenerateGlobals.ColumnType.switch_;
    /** 透明 */
    @Builder.Default
    @JSONField(serialize = false)
    private boolean isOpacity = false;
    /** 默认值 */
    @Builder.Default
    @JSONField(serialize = false)
    private boolean defaultValue = false;
    /** 开启 */
    @Builder.Default
    @JSONField(serialize = false)
    private String activeText = "开启";
    /** 关闭 */
    @Builder.Default
    @JSONField(serialize = false)
    private String inactiveText = "关闭";
    /** 颜色 */
    @Builder.Default
    @JSONField(serialize = false)
    private String activeColor = "#04f";
    /** 颜色 */
    @Builder.Default
    @JSONField(serialize = false)
    private String inactiveColor = "#f00";

    @JSONField(name = "config")
    @Override
    public Map<String, Object> getConfig(){
        Map<String, Object> config = super.getConfig();
        config.put("isOpacity", isOpacity());
        config.put("defaultValue", isDefaultValue());
        config.put("activeText", getActiveText());
        config.put("inactiveText", getInactiveText());
        config.put("activeColor", getActiveColor());
        config.put("inactiveColor", getInactiveColor());

        return config;
    }

    @JSONField(name = "type")
    public String getTypeName(){
        return type.getTypeName();
    }
}
