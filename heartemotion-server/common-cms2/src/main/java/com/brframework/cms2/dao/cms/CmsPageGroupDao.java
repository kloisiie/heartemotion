package com.brframework.cms2.dao.cms;

import com.brframework.cms2.entity.cms.CmsPageGroup;
import com.brframework.commondb.core.CommonRepository;
import org.springframework.stereotype.Repository;

/**
 * @Description 页面组
 * @Author xu
 * @Date 2020/11/07 16:05:05
 */

@Repository
public interface CmsPageGroupDao extends CommonRepository<Long, CmsPageGroup> {


}