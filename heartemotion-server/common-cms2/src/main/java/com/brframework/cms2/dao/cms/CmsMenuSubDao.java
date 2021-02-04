package com.brframework.cms2.dao.cms;

import com.brframework.cms2.entity.cms.CmsMenuSub;
import com.brframework.commondb.core.CommonRepository;
import org.springframework.stereotype.Repository;

/**
 * @Description 子菜单
 * @Author xu
 * @Date 2020/11/07 16:05:05
 */

@Repository
public interface CmsMenuSubDao extends CommonRepository<Long, CmsMenuSub> {


}