package com.brframework.cms2.generate.column;

import com.alibaba.fastjson.annotation.JSONField;
import com.brframework.cms2.generate.GenerateGlobals;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.Map;

/**
 * 级联
 * @author xu
 * @date 2020/12/8 11:54
 */
@SuperBuilder
@Getter
public class CascaderColumnEntry extends ColumnEntry {

    /** 列类型 */
    @Builder.Default
    private GenerateGlobals.ColumnType type = GenerateGlobals.ColumnType.cascader;
    /** 默认值 */
    @Builder.Default
    @JSONField(serialize = false)
    private String defaultValue = "";

    /** 动态 */
    @Builder.Default
    @JSONField(serialize = false)
    private boolean dynamic = true;

    /** 支持多选 */
    @Builder.Default
    @JSONField(serialize = false)
    private boolean multiple = false;

    /** 可搜索 */
    @Builder.Default
    @JSONField(serialize = false)
    private boolean filterable = false;

    /** 懒加载 */
    @Builder.Default
    @JSONField(serialize = false)
    private boolean lazy = false;

    /** 是否严格准守父子节点不互相关联 */
    @Builder.Default
    @JSONField(serialize = false)
    private boolean checkStrictly = false;

    /** 是否显示完全路径 */
    @Builder.Default
    @JSONField(serialize = false)
    private boolean showAllLevels = false;

    /** 动态数据访问地址 */
    @Builder.Default
    @JSONField(serialize = false)
    private String dynamicUrl = "";

    /** 数据层级 */
    @Builder.Default
    @JSONField(serialize = false)
    private Integer level = 1;

    @JSONField(name = "config")
    @Override
    public Map<String, Object> getConfig(){
        Map<String, Object> config = super.getConfig();
        config.put("defaultValue", getDefaultValue());
        config.put("dynamic", isDynamic());
        config.put("multiple", isMultiple());
        config.put("filterable", isFilterable());
        config.put("lazy", isLazy());
        config.put("checkStrictly", isCheckStrictly());
        config.put("showAllLevels", isShowAllLevels());
        config.put("dynamicUrl", getDynamicUrl());
        config.put("level", getLevel());

        return config;
    }

}
