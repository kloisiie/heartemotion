package com.brframework.cms2.dao.cms;

import com.brframework.cms2.entity.cms.CmsPage;
import com.brframework.commondb.core.CommonRepository;
import org.springframework.stereotype.Repository;

/**
 * @Description 页面
 * @Author xu
 * @Date 2020/11/07 16:05:05
 */

@Repository
public interface CmsPageDao extends CommonRepository<Long, CmsPage> {


}