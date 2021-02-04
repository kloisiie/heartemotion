package com.brframework.cms2.generate.layout;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.annotation.JSONField;
import com.brframework.cms2.generate.ButtonEntry;
import com.brframework.cms2.generate.GenerateGlobals;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Map;

/**
 * 分页列表布局
 * @author xu
 * @date 2020/12/8 14:36
 */
@SuperBuilder
@Getter
public class ListLayoutEntry extends LayoutEntry  {
    @Builder.Default
    private GenerateGlobals.LayoutType type = GenerateGlobals.LayoutType.list;
    @Builder.Default
    private String name = "功能栏";
    /** 搜索URL */
    @Builder.Default
    @JSONField(serialize = false)
    private String searchUrl = "";

    /** 操作栏布局 */
    @JSONField(serialize = false)
    private ActionLayoutEntry actionLayout;
    /** 搜索栏布局 */
    @JSONField(serialize = false)
    private SearchLayoutEntry searchLayout;
    /** 表格栏布局 */
    @JSONField(serialize = false)
    private TableLayoutEntry tableLayout;

    @JSONField(name = "config")
    public Map<String, String> getConfig(){
        Map<String, String> config = Maps.newHashMap();
        config.put("prop", getKey());
        config.put("searchUrl", getSearchUrl());
        return config;
    }

    @JSONField(name = "columns")
    public List<Object> getColumns(){
        List<Object> list = Lists.newArrayList();
        if(actionLayout != null && actionLayout.getColumns().size() != 0){
            list.add(actionLayout);
        }

        if(searchLayout != null && searchLayout.getColumns().size() != 0){
            list.add(searchLayout);
        }
        list.add(tableLayout);
        return list;
    }
}
