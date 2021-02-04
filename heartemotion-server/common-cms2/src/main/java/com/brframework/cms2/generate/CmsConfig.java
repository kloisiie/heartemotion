package com.brframework.cms2.generate;

import com.google.common.collect.Lists;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * CMS 配置
 * @author xu
 * @date 2020/12/8 19:05
 */
@SuperBuilder
@Getter
public class CmsConfig {

    @Builder.Default
    private List<String> context = Lists.newArrayList();

}
