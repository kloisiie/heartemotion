package com.brframework.cms2.generate.layout;

import cn.hutool.core.util.IdUtil;
import com.brframework.cms2.generate.GenerateGlobals;
import com.brframework.cms2.generate.column.ColumnEntry;
import com.google.common.collect.Lists;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * @author xu
 * @date 2020/12/8 11:51
 */
@SuperBuilder
@Getter
public class ColLayoutEntry extends LayoutEntry  {
    @Builder.Default
    private GenerateGlobals.LayoutType type = GenerateGlobals.LayoutType.col;
    @Builder.Default
    private List<ColumnEntry> columns = Lists.newArrayList();
}
