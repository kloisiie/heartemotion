package com.brframework.cms2.generate.column;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.annotation.JSONField;
import com.brframework.cms2.generate.ButtonEntry;
import com.google.common.collect.Lists;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 列操作栏
 * @author xu
 * @date 2020/12/9 16:01
 */
@SuperBuilder
@Getter
public class TableOperationColumnEntry {

    /** 列标签 */
    @Builder.Default
    private String label = "操作";
    /** 列宽度 */
    @Builder.Default
    private String width = "250";
    @Builder.Default
    private String belong = "normal";
    @Builder.Default
    private String type = "table-operation";
    @Builder.Default
    private String key = IdUtil.simpleUUID();
    @Builder.Default
    private boolean fixRight = false;
    @Builder.Default
    private List<ButtonEntry> columns = Lists.newArrayList();

    @JSONField(name = "config")
    public Map<String, Object> getConfig(){
        Map<String, Object> config = new HashMap<>();
        config.put("prop", "operation");
        config.put("label", getLabel());
        config.put("width", getWidth());
        config.put("fixRight", isFixRight());

        return config;
    }

}
