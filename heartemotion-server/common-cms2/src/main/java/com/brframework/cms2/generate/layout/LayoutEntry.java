package com.brframework.cms2.generate.layout;

import cn.hutool.core.util.IdUtil;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

/**
 * 布局
 * @author xu
 * @date 2020/12/8 19:00
 */
@SuperBuilder
@Getter
public class LayoutEntry {
    @Builder.Default
    private String belong = "normal";
    @Builder.Default
    private String key = IdUtil.simpleUUID();

}
