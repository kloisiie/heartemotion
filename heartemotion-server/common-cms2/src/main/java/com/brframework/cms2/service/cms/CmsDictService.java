package com.brframework.cms2.service.cms;

import com.brframework.cms2.core.CmsDict;

/**
 * Cms字典服务
 * @author xu
 * @date 2020/11/17 14:41
 */
public interface CmsDictService {

    /**
     * 获取Cms字典
     * @param key  字典Key
     * @return  Cms字典
     */
    CmsDict getCmsDict(String key);

}
