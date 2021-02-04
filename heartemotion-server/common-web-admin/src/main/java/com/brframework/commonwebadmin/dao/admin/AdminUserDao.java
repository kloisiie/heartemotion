package com.brframework.commonwebadmin.dao.admin;


import com.brframework.commondb.core.CommonRepository;
import com.brframework.commonwebadmin.entity.admin.AdminUser;
import org.springframework.stereotype.Repository;

/**
 * @author xu
 * @date 2018/3/12 0012 下午 9:06
 * 后台用户
 */
@Repository
public interface AdminUserDao extends CommonRepository<Integer, AdminUser>{

    /**
     * 通过用户名查询
     * @param username
     * @return
     */
    AdminUser findByUsername(String username);


}
