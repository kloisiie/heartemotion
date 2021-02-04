package com.brframework.cms2.generate.column;

import com.alibaba.fastjson.annotation.JSONField;
import com.brframework.cms2.generate.GenerateGlobals;
import com.google.common.collect.Lists;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Map;

/**
 * 媒体文件上传
 * @author xu
 * @date 2020/12/8 11:55
 */
@SuperBuilder
@Getter
public class MediaUploadColumnEntry extends ColumnEntry {
    /** 列类型 */
    @Builder.Default
    @JSONField(serialize = false)
    private GenerateGlobals.ColumnType type = GenerateGlobals.ColumnType.media_upload;

    /** 默认值 */
    @Builder.Default
    @JSONField(serialize = false)
    private List<String> defaultValue = Lists.newArrayList();

    /** 占位内容 */
    @Builder.Default
    @JSONField(serialize = false)
    private String placeholder = "";

    /** 上传文件限制 */
    @Builder.Default
    @JSONField(serialize = false)
    private Integer limit = 10;

    /** 多文件 */
    @Builder.Default
    @JSONField(serialize = false)
    private boolean multiple = false;

    /** 上传大小限制(单位M) */
    @Builder.Default
    @JSONField(serialize = false)
    private Integer sizeLimit = 500;

    /** 匹配 */
    @Builder.Default
    @JSONField(serialize = false)
    private String pattern = "";

    @JSONField(name = "config")
    @Override
    public Map<String, Object> getConfig(){
        Map<String, Object> config = super.getConfig();
        config.put("defaultValue", getDefaultValue());
        config.put("placeholder", getPlaceholder());
        config.put("multiple", isMultiple());
        config.put("sizeLimit", getSizeLimit());
        config.put("limit", getLimit());
        config.put("pattern", getPattern());
        return config;
    }

    @JSONField(name = "type")
    public String getTypeName(){
        return type.getTypeName();
    }
}
