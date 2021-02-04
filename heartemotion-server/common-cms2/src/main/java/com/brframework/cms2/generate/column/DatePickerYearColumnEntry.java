package com.brframework.cms2.generate.column;

import com.alibaba.fastjson.annotation.JSONField;
import com.brframework.cms2.generate.GenerateGlobals;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.Map;

/**
 * 年选择框
 * @author xu
 * @date 2020/12/8 11:53
 */
@SuperBuilder
@Getter
public class DatePickerYearColumnEntry extends ColumnEntry {

    /** 列类型 */
    @Builder.Default
    @JSONField(serialize = false)
    private GenerateGlobals.ColumnType type = GenerateGlobals.ColumnType.date_picker_year;
    /** 默认值 */
    @Builder.Default
    @JSONField(serialize = false)
    private String defaultValue = "";
    /** 默认值 */
    @Builder.Default
    @JSONField(serialize = false)
    private String dateType = "year";

    /** 时间格式 */
    @Builder.Default
    @JSONField(serialize = false)
    private String format = "yyyy";

    @JSONField(name = "config")
    @Override
    public Map<String, Object> getConfig(){
        Map<String, Object> config = super.getConfig();
        config.put("type", getDateType());
        config.put("defaultValue", getDefaultValue());
        config.put("format", getFormat());

        return config;
    }

    @JSONField(name = "type")
    public String getTypeName(){
        return type.getTypeName();
    }
}
