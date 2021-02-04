package com.brframework.cms2.generate;

import com.brframework.cms2.generate.layout.LayoutEntry;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * CMS 抽象页面
 * @author xu
 * @date 2020/12/8 18:40
 */
@SuperBuilder
@Getter
public class CmsPage {

    /** 页面布局 */
    private List<LayoutEntry> page;

    /** 页面配置 */
    private CmsConfig config;

}
