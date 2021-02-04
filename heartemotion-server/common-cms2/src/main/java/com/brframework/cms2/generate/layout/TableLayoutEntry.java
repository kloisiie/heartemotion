package com.brframework.cms2.generate.layout;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.annotation.JSONField;
import com.brframework.cms2.generate.GenerateGlobals;
import com.brframework.cms2.generate.column.TableColumnEntry;
import com.brframework.cms2.generate.column.TableOperationColumnEntry;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Map;

/**
 * 表格布局
 * @author xu
 * @date 2020/12/8 14:29
 */
@SuperBuilder
@Getter
public class TableLayoutEntry extends LayoutEntry  {


    @Builder.Default
    private GenerateGlobals.LayoutType type = GenerateGlobals.LayoutType.table;
    @Builder.Default
    private String icon = "table";
    @Builder.Default
    private String name = "表格布局";
    /** 是否存在分液器 */
    @Builder.Default
    private boolean pagination = true;
    /** 是否存在多行选择框 */
    @Builder.Default
    private boolean selection = true;
    /** 高度 */
    @Builder.Default
    private int height = 500;
    @Builder.Default
    @JSONField(serialize = false)
    private List<TableColumnEntry> columns = Lists.newArrayList();
    /** 操作栏 */
    @JSONField(serialize = false)
    private TableOperationColumnEntry operation;

    @JSONField(name = "config")
    public Map<String, Object> getConfig(){
        Map<String, Object> config = Maps.newHashMap();
        config.put("prop", getKey());
        config.put("pagination", isPagination());
        config.put("selection", isSelection());
        config.put("height", getHeight());
        return config;
    }

    @JSONField(name = "columns")
    public List<Object> getColumns(){
        if(getOperation() == null){
            return Lists.newArrayList(columns);
        } else {
            List<Object> list = Lists.newArrayList(columns);

            if(getOperation() != null && getOperation().getColumns() != null &&
                    getOperation().getColumns().size() != 0){
                list.add(getOperation());
            }

            return list;
        }
    }


}
