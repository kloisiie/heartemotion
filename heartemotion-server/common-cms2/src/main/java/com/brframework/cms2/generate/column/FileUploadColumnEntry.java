package com.brframework.cms2.generate.column;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.annotation.JSONField;
import com.brframework.cms2.generate.GenerateGlobals;
import com.google.common.collect.Lists;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Map;

/**
 * 文件上传
 * @author xu
 * @date 2020/12/8 11:55
 */
@SuperBuilder
@Getter
public class FileUploadColumnEntry extends ColumnEntry {

    /** 列类型 */
    @Builder.Default
    @JSONField(serialize = false)
    private GenerateGlobals.ColumnType type = GenerateGlobals.ColumnType.file_upload;

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
    private Integer sizeLimit = 50;


    /** 文件上传类型限制 */
    @Builder.Default
    @JSONField(serialize = false)
    private List<String> typeLimit = Lists.newArrayList("all");

    @JSONField(name = "config")
    @Override
    public Map<String, Object> getConfig(){
        Map<String, Object> config = super.getConfig();
        config.put("defaultValue", getDefaultValue());
        config.put("placeholder", getPlaceholder());
        config.put("multiple", isMultiple());
        config.put("sizeLimit", getSizeLimit());
        config.put("limit", getLimit());
        config.put("typeLimit", getTypeLimit());
        config.put("typeOptions", getTypeOptions());
        return config;
    }

    @JSONField(serialize = false)
    public JSONArray getTypeOptions(){
        return JSON.parseArray("[\n" +
                "                      {\n" +
                "                        \"value\": \"all\",\n" +
                "                        \"label\": \"不限\"\n" +
                "                      },\n" +
                "                      {\n" +
                "                        \"value\": \"word\",\n" +
                "                        \"label\": \"word文档\"\n" +
                "                      },\n" +
                "                      {\n" +
                "                        \"value\": \"excel\",\n" +
                "                        \"label\": \"excel文件\"\n" +
                "                      },\n" +
                "                      {\n" +
                "                        \"value\": \"ppt\",\n" +
                "                        \"label\": \"ppt文件\"\n" +
                "                      },\n" +
                "                      {\n" +
                "                        \"value\": \"pdf\",\n" +
                "                        \"label\": \"pdf文件\"\n" +
                "                      },\n" +
                "                      {\n" +
                "                        \"value\": \"txt\",\n" +
                "                        \"label\": \"txt文件\"\n" +
                "                      }\n" +
                "                    ]");
    }

    @JSONField(name = "type")
    public String getTypeName(){
        return type.getTypeName();
    }

}
