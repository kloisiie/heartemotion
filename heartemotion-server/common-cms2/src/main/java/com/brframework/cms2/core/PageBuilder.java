package com.brframework.cms2.core;

import javax.servlet.http.HttpServletRequest;

/**
 * 页面构建器
 *
 *
 * @author xu
 * @date 2020/10/29 14:09
 */
public interface PageBuilder {

    /**
     * 构建
     * @param request  构建请求
     * @return  页面JSON
     */
    String build(HttpServletRequest request);

}
