package com.brframework.cms2.generate;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Builder;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * 按钮
 * @author xu
 * @date 2020/12/8 11:56
 */
@Getter
@Builder
public class ButtonEntry {

    @JSONField(serialize = false)
    private String label;
    @JSONField(serialize = false)
    private String url;

    @Builder.Default
    private String key = IdUtil.simpleUUID();

    @Builder.Default
    private String belong = "normal";
    @Builder.Default
    private String type = "button";

    @Builder.Default
    @JSONField(serialize = false)
    private String icon = "";
    @Builder.Default
    @JSONField(serialize = false)
    private String condition = "";
    @Builder.Default
    @JSONField(serialize = false)
    private String role = "";
    @Builder.Default
    @JSONField(serialize = false)
    private boolean valid = false;
    @Builder.Default
    @JSONField(serialize = false)
    private boolean isCircle = false;
    @Builder.Default
    @JSONField(serialize = false)
    private boolean selection = false;
    @Builder.Default
    @JSONField(serialize = false)
    private boolean plain = false;
    @JSONField(serialize = false)
    @Builder.Default
    private GenerateGlobals.ButtonStyle btnStyle = GenerateGlobals.ButtonStyle.primary;
    @JSONField(serialize = false)
    @Builder.Default
    private GenerateGlobals.ButtonType btnType = GenerateGlobals.ButtonType.text;


    @JSONField(name = "config")
    public Map<String, Object> getConfig(){
        Map<String, Object> config = new HashMap<>();
        config.put("valid", isValid());
        config.put("condition", getCondition());
        config.put("role", getRole());
        config.put("isCircle", isCircle());
        config.put("selection", isSelection());
        config.put("btnStyle", getBtnStyle());
        config.put("plain", isPlain());
        config.put("prop", getKey());
        config.put("icon", getIcon());
        config.put("label", getLabel());
        config.put("btnType", getBtnType());
        config.put("url", getUrl());

        return config;
    }

}
