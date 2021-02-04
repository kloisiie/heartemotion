package com.brframework.cms2.generate.column;

import com.alibaba.fastjson.annotation.JSONField;
import com.brframework.cms2.generate.GenerateGlobals;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.Map;

/**
 * 日期时间选择框
 * @author xu
 * @date 2020/12/8 11:54
 */
@SuperBuilder
@Getter
public class DateTimePickerColumnEntry extends ColumnEntry {

    /** 列类型 */
    @Builder.Default
    @JSONField(serialize = false)
    private GenerateGlobals.ColumnType type = GenerateGlobals.ColumnType.date_time_picker;
    /** 默认值 */
    @Builder.Default
    @JSONField(serialize = false)
    private String defaultValue = "";

    /** 是否是范围选择 */
    @Builder.Default
    @JSONField(serialize = false)
    private boolean isRange = false;


    @JSONField(name = "config")
    @Override
    public Map<String, Object> getConfig(){
        Map<String, Object> config = super.getConfig();
        config.put("defaultValue", getDefaultValue());
        config.put("isRange", isRange);

        return config;
    }

    @JSONField(name = "type")
    public String getTypeName(){
        return type.getTypeName();
    }

}
