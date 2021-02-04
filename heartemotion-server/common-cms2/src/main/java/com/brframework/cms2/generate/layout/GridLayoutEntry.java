package com.brframework.cms2.generate.layout;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.annotation.JSONField;
import com.brframework.cms2.generate.GenerateGlobals;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Map;

/**
 * 格栅布局
 * @author xu
 * @date 2020/12/8 11:45
 */
@SuperBuilder
@Getter
public class GridLayoutEntry extends LayoutEntry  {
    @Builder.Default
    private GenerateGlobals.LayoutType type = GenerateGlobals.LayoutType.grid;
    @Builder.Default
    private List<ColLayoutEntry> columns = Lists.newArrayList();

    @JSONField(name = "config")
    public Map<String, String> getConfig(){
        Map<String, String> config = Maps.newHashMap();
        config.put("prop", getKey());
        return config;
    }
}
