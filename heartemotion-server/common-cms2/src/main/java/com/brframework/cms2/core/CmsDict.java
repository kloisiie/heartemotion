package com.brframework.cms2.core;

import com.brframework.cms2.json.admin.cms.CmsDictEntry;

import java.util.List;

/**
 * Cms字典
 * @author xu
 * @date 2020/11/17 14:43
 */
public interface CmsDict {

    /**
     * 字典Key
     * @return  字典Key
     */
    String dictKey();

    /**
     * 字典内容
     * @param childKeys
     * @return  字典内容
     */
    List<CmsDictEntry> dictEntry(String... childKeys);

}
