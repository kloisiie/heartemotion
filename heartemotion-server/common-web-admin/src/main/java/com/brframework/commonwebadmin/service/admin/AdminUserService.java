package com.brframework.commonwebadmin.service.admin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.brframework.cms2.entity.cms.CmsMenuFun;
import com.brframework.commondb.core.AbstractEntityService;
import com.brframework.commondb.core.CommonRepository;
import com.brframework.commonsecurity.core.SecuritySubject;
import com.brframework.commonsecurity.core.SecurityUserDetailsService;
import com.brframework.commonweb.exception.HandleException;
import com.brframework.commonwebadmin.dao.admin.AdminUserDao;
import com.brframework.commonwebadmin.entity.admin.AdminRole;
import com.brframework.commonwebadmin.entity.admin.AdminUser;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author xu
 * @date 2018/3/12 0012 下午 9:07
 * 后台用户
 */
@Service
public class AdminUserService extends AbstractEntityService<AdminUser, Integer, Object> {

    @Autowired
    AdminUserDao adminUserDao;
    @Autowired
    AdminRoleService adminRoleService;
    @Autowired
    FunctionService functionService;

    @Autowired
    @Qualifier("adminUserDetailsService")
    SecurityUserDetailsService userDetailsService;

    @Override
    public CommonRepository<Integer, AdminUser> getRepository() {
        return adminUserDao;
    }


    public AdminUser findByUsername(String username){
        return adminUserDao.findByUsername(username);
    }

    /**
     * 用户登录
     * @param username
     * @param password
     * @return token
     */
    public String login(String username, String password) {

        AdminUser user = adminUserDao.findByUsername(username);
        if (user == null) {
            //账户不存在
            throw new HandleException("用户名或密码错误");
        }
        if (user.getState().equals(0)) {
            throw new HandleException("该账号已禁用!");
        }

        if (! new BCryptPasswordEncoder().matches(password, user.getPassword())) {
            //密码错误
            throw new HandleException("用户名或密码错误");
        }
        return genJwt(user, Maps.newHashMap());
    }

    /**
     * 获取用户所有权限
     * @param userId
     * @return
     */
    public List<String> getAuthorities(Integer userId){
        AdminUser adminUser = accessObject(userId);
        Set<String> authorities = Sets.newHashSet();

        if(!Strings.isNullOrEmpty(adminUser.getRoles())){
            List<String> roles = JSON.parseObject(adminUser.getRoles(),
                    new TypeReference<List<String>>(){});
            X:for (String role : roles) {
                AdminRole rolePermission = adminRoleService.accessObject(Integer.parseInt(role));
                List<String[]> permissions = JSON.parseObject(rolePermission.getPermissions(),
                        new TypeReference<List<String[]>>(){});

                for (String[] permission : permissions) {

                    if(StringUtils.equals(permission[2], "*")){
                        authorities.clear();
                        authorities.addAll(getAllPermission(functionService.getMenuFunList()));
                        break X;
                    }

                    authorities.add("ROLE_" + permission[2]);
                }
            }
        }
        return Lists.newArrayList(authorities);
    }

    /**
     * 生成JWT
     * @param user
     * @return
     */
    public String genJwt(AdminUser user, Map<String, String> expand) {
        return userDetailsService.genToken(user.getUsername(), SecuritySubject.builder()
                .id(user.getId().toString())
                .username(user.getUsername())
                .expand(expand)
                .build());
    }

    private List<String> getAllPermission(List<CmsMenuFun> menuFunList){
        if(menuFunList == null || menuFunList.size() == 0){
            return Collections.EMPTY_LIST;
        }
        List<String> pers = Lists.newArrayList();
        for (CmsMenuFun f : menuFunList) {
            pers.add("ROLE_" + f.getRole());
        }
        return pers;
    }

    /**
     * 更新用户信息
     * @param adminUserId
     * @param nickname
     * @param roles
     */
    public void updateUser(Integer adminUserId ,String nickname, String roles){
        AdminUser user = accessObject(adminUserId);
        user.setNickname(nickname);
        user.setRoles(roles);

        adminUserDao.save(user);
    }

    /**
     * 更新用户密码
     * @param adminUserId
     * @param password
     */
    public void updateUserPassword(Integer adminUserId, String password){
        AdminUser user = accessObject(adminUserId);

        user.setPassword(new BCryptPasswordEncoder().encode(password));

        adminUserDao.save(user);
    }

    public void disableUser(Integer adminUserId){
        AdminUser adminUser = accessObject(adminUserId);
        if (adminUser.getState().equals(0)) {
            throw new HandleException("用户已经是禁用状态");
        }
        adminUser.setState(0);
        save(adminUser);
//        userDetailsService.expiredToken(adminUser.getUsername());
    }


    public void enableUser(Integer adminUserId){
        AdminUser adminUser = accessObject(adminUserId);
        if (adminUser.getState().equals(1)) {
            throw new HandleException("用户已经是正常状态");
        }
        adminUser.setState(1);
        save(adminUser);
    }
}
