package com.brframework.cms2.generate.column;

import com.alibaba.fastjson.annotation.JSONField;
import com.brframework.cms2.generate.GenerateGlobals;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.Map;

/**
 * 评分
 * @author xu
 * @date 2020/12/8 11:54
 */
@SuperBuilder
@Getter
public class RateColumnEntry extends ColumnEntry {

    /** 列类型 */
    @Builder.Default
    private GenerateGlobals.ColumnType type = GenerateGlobals.ColumnType.rate;
    /** 默认值 */
    @Builder.Default
    @JSONField(serialize = false)
    private Integer defaultValue = 0;

    /** 最大分数 */
    @Builder.Default
    @JSONField(serialize = false)
    private Integer max = 5;

    /** 是否允许半分 */
    @Builder.Default
    @JSONField(serialize = false)
    private boolean allowHalf = false;

    /** 是否显示分数 */
    @Builder.Default
    @JSONField(serialize = false)
    private boolean showScore = false;

    @JSONField(name = "config")
    @Override
    public Map<String, Object> getConfig(){
        Map<String, Object> config = super.getConfig();
        config.put("defaultValue", getDefaultValue());
        config.put("max", getMax());
        config.put("allowHalf", isAllowHalf());
        config.put("showScore", isShowScore());

        return config;
    }

}
