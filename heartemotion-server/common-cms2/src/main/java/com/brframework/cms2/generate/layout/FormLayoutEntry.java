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
 * 表单布局
 * @author xu
 * @date 2020/12/8 18:52
 */
@SuperBuilder
@Getter
public class FormLayoutEntry extends LayoutEntry  {

    @Builder.Default
    private GenerateGlobals.LayoutType type = GenerateGlobals.LayoutType.form;
    @Builder.Default
    private List<LayoutEntry> columns = Lists.newArrayList();
    /** 是否可编辑 */
    @Builder.Default
    private boolean editable = true;

    @JSONField(name = "config")
    public Map<String, Object> getConfig(){
        Map<String, Object> config = Maps.newHashMap();
        config.put("prop", getKey());
        config.put("editable", isEditable());
        return config;
    }

}
