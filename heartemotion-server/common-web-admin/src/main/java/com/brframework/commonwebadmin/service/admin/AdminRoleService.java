package com.brframework.commonwebadmin.service.admin;

import com.brframework.commondb.core.AbstractEntityService;
import com.brframework.commondb.core.CommonRepository;
import com.brframework.commonwebadmin.dao.admin.AdminRoleDao;
import com.brframework.commonwebadmin.entity.admin.AdminRole;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 角色管理
 *
 * @author xu
 * @date 2019/11/11 11:19
 */
@Service
public class AdminRoleService extends AbstractEntityService<AdminRole, Integer, Object>{

    @Autowired
    AdminRoleDao adminRoleDao;

    @Override
    public CommonRepository<Integer, AdminRole> getRepository() {
        return adminRoleDao;
    }

    /**
     * 创建角色
     *
     * @param name
     * @param permisson
     */
    public AdminRole create(String name, String permisson) {
        AdminRole role = new AdminRole();
        role.setCreateDate(LocalDateTime.now());
        role.setName(name);
        role.setPermissions(permisson);
        return adminRoleDao.save(role);
    }

    /**
     * 更新角色
     *
     * @param id
     * @param name
     * @param permission
     */
    public void update(Integer id, String name, String permission) {
        AdminRole role = accessObject(id);
        role.setName(name);
        role.setPermissions(permission);
        adminRoleDao.save(role);
    }
}
