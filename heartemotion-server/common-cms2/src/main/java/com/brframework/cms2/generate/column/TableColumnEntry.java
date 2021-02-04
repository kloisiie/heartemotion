package com.brframework.cms2.generate.column;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.annotation.JSONField;
import com.brframework.cms2.generate.GenerateGlobals;
import com.google.common.collect.Maps;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * 表列
 * @author xu
 * @date 2020/12/8 12:35
 */
@SuperBuilder
@Getter
public class TableColumnEntry {

    /** 列标识 */
    private String prop;
    /** 列标签 */
    private String label;
    /** 列宽度 */
    @Builder.Default
    private String width = "";
    @Builder.Default
    private String belong = "normal";
    @Builder.Default
    private String name = "表格子项";
    @Builder.Default
    private String icon = "table-text";
    @Builder.Default
    private String type = "table-text";
    /** 列最小宽度 */
    @Builder.Default
    @JSONField(serialize = false)
    private String minWidth = "";
    /** 列类型 */
    @Builder.Default
    @JSONField(serialize = false)
    private GenerateGlobals.TableColumnType columnType = GenerateGlobals.TableColumnType.text;
    /** URL */
    @Builder.Default
    @JSONField(serialize = false)
    private String url = "/getAction/{id}";
    /** options */
    @Builder.Default
    @JSONField(serialize = false)
    private JSONArray options = JSON.parseArray("[{\"label\":\"文本\",\"value\":\"text\"},{\"label\":\"图片\",\"value\":\"image\"},{\"label\":\"操作\",\"value\":\"action\"}]");

    @Builder.Default
    private String key = IdUtil.simpleUUID();

    @JSONField(name = "config")
    public Map<String, Object> getConfig(){
        Map<String, Object> config = new HashMap<>();
        config.put("prop", getProp());
        config.put("label", getLabel());
        config.put("minWidth", getMinWidth());
        config.put("type", getColumnType());
        config.put("url", getUrl());
        config.put("width", getWidth());
        config.put("options", getOptions());

        return config;
    }
}
