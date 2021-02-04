package com.brframework.commonwebadmin.web.admin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.brframework.cms2.core.CmsDict;
import com.brframework.cms2.json.admin.cms.CmsDictEntry;
import com.brframework.commondb.core.ControllerPageHandle;
import com.brframework.commonweb.json.JSONResult;
import com.brframework.commonweb.json.Page;
import com.brframework.commonweb.json.PageParam;
import com.brframework.commonwebadmin.aop.annotation.AOLog;
import com.brframework.commonwebadmin.entity.admin.AdminOptionLog;
import com.brframework.commonwebadmin.entity.admin.AdminRole;
import com.brframework.commonwebadmin.entity.admin.AdminUser;
import com.brframework.commonwebadmin.json.admin.adminuser.*;
import com.brframework.commonwebadmin.service.admin.AdminOptionLogService;
import com.brframework.commonwebadmin.service.admin.AdminRoleService;
import com.brframework.commonwebadmin.service.admin.AdminUserService;
import com.brframework.commonwebadmin.service.admin.FunctionService;
import com.google.common.collect.Lists;
import com.google.common.primitives.Ints;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.brframework.commondb.core.ControllerPageHandle.jpaPageHandleToPage;
import static com.brframework.commondb.core.ControllerPageHandle.jpaPageToPage;

/**
 * @author xu
 * @date 2018/3/9 0009 下午 8:37
 * 后台用户管理(需要ADMIN权限)
 */
@RestController
@Api(tags = "系统用户")
@RequestMapping("/admin/access")
public class AdminUserRoleController {

    public static final String OPTION_ROLE_LIST = "OPTION_ROLE_LIST";

    @Autowired
    AdminUserService adminUserService;
    @Autowired
    AdminRoleService adminRoleService;
    @Autowired
    FunctionService functionService;


    @ApiOperation(value = "用户列表", notes = "用户列表")
    @GetMapping("v1/sys-user/list")
    @PreAuthorize("hasRole('sys-user-query')")
    public JSONResult<Page<AdminUserResult>> adminUserListV1(@Valid PageParam page) {
         return JSONResult.ok(jpaPageHandleToPage(adminUserService.page(page, null), page, from -> {
            AdminUserResult result = new AdminUserResult();
            result.setId(from.getId());
            result.setCreateDate(from.getCreateDate());
            result.setUsername(from.getUsername());
            result.setNickname(from.getNickname());
            result.setState(from.getState());
            result.setStateName(from.getState() == 0 ? "禁用" : "正常");
            JSONArray roles = JSON.parseArray(from.getRoles());
            result.setRoles(Lists.newArrayList());
            for (int i = 0; i < roles.size(); i++) {
                AdminRole adminRole = adminRoleService.accessObject(roles.getInteger(i));
                result.getRoles().add(adminRole.getName());
            }
            return result;
        }));
    }

    @ApiOperation(value = "用户信息", notes = "用户信息")
    @GetMapping("v1/sys-user/{id}")
    @PreAuthorize("hasRole('sys-user-query')")
    public JSONResult<AdminUserDetailsResult> adminUserInfo(@PathVariable Integer id){

        AdminUser from = adminUserService.accessObject(id);
        AdminUserDetailsResult result = new AdminUserDetailsResult();
        result.setId(from.getId());
        result.setCreateDate(from.getCreateDate());
        result.setUsername(from.getUsername());
        result.setNickname(from.getNickname());
        result.setState(from.getState());
        result.setStateName(from.getState() == 0 ? "禁用" : "正常");
        JSONArray roles = JSON.parseArray(from.getRoles());
        result.setRoles(Lists.newArrayList());
        for (int i = 0; i < roles.size(); i++) {
            result.getRoles().add(new String[]{roles.getInteger(i) + ""});
        }

        return JSONResult.ok(
                result
        );
    }

    @ApiOperation(value = "用户信息", notes = "用户信息")
    @GetMapping("v1/sys-user/id/{userId}")
    @PreAuthorize("hasRole('sys-user-query')")
    public JSONResult<AdminUserDetailsResult> adminUserInfoByUserId(@PathVariable Integer userId){
        return adminUserInfo(userId);
    }

    @ApiOperation(value = "更新用户信息", notes = "更新用户信息")
    @PostMapping("v1/sys-user/{id}")
    @PreAuthorize("hasRole('sys-user-update')")
    @AOLog
    public JSONResult adminUserEditV1(
            @PathVariable Integer id,
            @Valid AdminUserUpdateParam param) {
        adminUserService.updateUser(id, param.getNickname(),
                JSON.toJSONString(param.getRoles().stream().map(p -> p[0]).collect(Collectors.toList())));

        return JSONResult.ok();
    }

    @ApiOperation(value = "禁用用户", notes = "禁用用户")
    @PostMapping("v1/sys-user/disable/{id}")
    @PreAuthorize("hasRole('sys-user-update')")
    @AOLog
    public JSONResult adminUserDisableV1(@PathVariable Integer id) {

        adminUserService.disableUser(id);

        return JSONResult.ok();
    }

    @ApiOperation(value = "批量禁用用户", notes = "批量禁用用户")
    @PostMapping("v1/sys-user/disable/batch")
    @PreAuthorize("hasRole('sys-user-update')")
    @AOLog
    public JSONResult adminUserBatchDisableV1(@RequestParam("ids") List<Integer> ids) {

        for (Integer id : ids) {
            adminUserService.disableUser(id);
        }

        return JSONResult.ok();
    }

    @ApiOperation(value = "启用用户", notes = "设置为启用状态")
    @PostMapping("v1/sys-user/enable/{id}")
    @PreAuthorize("hasRole('sys-user-update')")
    @AOLog
    public JSONResult adminUserEnableV1(@PathVariable Integer id) {

        adminUserService.enableUser(id);

        return JSONResult.ok();
    }

    @ApiOperation(value = "批量启用用户", notes = "设置为启用状态")
    @PostMapping("v1/sys-user/enable/batch")
    @PreAuthorize("hasRole('sys-user-update')")
    @AOLog
    public JSONResult adminUserBatchEnableV1(@RequestParam("ids") List<Integer> ids) {

        for (Integer id : ids) {
            adminUserService.enableUser(id);
        }
        return JSONResult.ok();
    }

    @ApiOperation(value = "重置用户密码", notes = "重置用户密码")
    @PostMapping("v1/sys-user/{id}/password")
    @PreAuthorize("hasRole('sys-user-update')")
    @AOLog
    public JSONResult adminUserEditPasswordV1(
            @PathVariable Integer id,
            @Valid AdminUserPasswordParam param) {

        adminUserService.updateUserPassword(id, param.getPassword());

        return JSONResult.ok();
    }

    @ApiOperation(value = "添加用户", notes = "添加用户")
    @PutMapping("v1/sys-user")
    @PreAuthorize("hasRole('sys-user-add')")
    @AOLog
    public JSONResult adminUserCreateV1(@Valid AdminUserCreateParam param) {

        adminUserService.save(AdminUser.builder()
                .createDate(LocalDateTime.now())
                .nickname(param.getNickname())
                .password(new BCryptPasswordEncoder().encode(param.getPassword()))
                .roles(JSON.toJSONString(param.getRoles().stream().map(p -> p[0]).collect(Collectors.toList())))
                .username(param.getUsername())
                .state(1)
                .build());

        return JSONResult.ok();
    }

    @ApiOperation(value = "删除用户", notes = "删除用户")
    @DeleteMapping("v1/sys-user/{id}")
    @PreAuthorize("hasRole('sys-user-del')")
    @AOLog
    public JSONResult adminUserDeleteV1(@PathVariable String id) {
        adminUserService.removeById(Ints.tryParse(id));
        return JSONResult.ok();
    }

    @ApiOperation(value = "批量删除用户", notes = "批量删除用户")
    @DeleteMapping("v1/sys-user/batch-del")
    @PreAuthorize("hasRole('sys-user-del')")
    @AOLog
    public JSONResult adminUserBatchDeleteV1(@RequestParam("ids") List<Integer> ids) {
        for (Integer id : ids) {
            adminUserService.removeById(id);
        }
        return JSONResult.ok();
    }

    @ApiOperation(value = "角色列表", notes = "角色列表")
    @GetMapping("v1/sys-role/list")
    @PreAuthorize("hasRole('sys-role-query')")
    public JSONResult<Page<AdminRole>> adminRoleListV1(@Valid PageParam page) {

        return JSONResult.ok(jpaPageToPage(
                adminRoleService.page(page, null),
                page
        ));
    }

    @ApiOperation(value = "创建角色", notes = "创建角色")
    @PutMapping("v1/sys-role")
    @PreAuthorize("hasRole('sys-role-add')")
    @AOLog
    public JSONResult adminRoleCreateV1(@Valid AdminRoleEditParam param) {
        adminRoleService.create(param.getName(), param.getPermissions());
        return JSONResult.ok();
    }

    @ApiOperation(value = "角色详情", notes = "角色详情")
    @GetMapping("v1/sys-role/{id}")
    @PreAuthorize("hasRole('sys-role-query')")
    @AOLog
    public JSONResult<AdminRole> adminRoleGetV1(@PathVariable String id) {
        return JSONResult.ok(adminRoleService.accessObject(Integer.parseInt(id)));
    }

    @ApiOperation(value = "编辑角色", notes = "编辑角色")
    @PostMapping("v1/sys-role/{id}")
    @PreAuthorize("hasRole('sys-role-update')")
    @AOLog
    public JSONResult adminRoleEditV1(@PathVariable String id,@Valid AdminRoleEditParam param) {
        adminRoleService.update(Integer.parseInt(id), param.getName(), param.getPermissions());
        return JSONResult.ok();
    }

    @ApiOperation(value = "删除角色", notes = "删除角色")
    @DeleteMapping("v1/sys-role/{id}")
    @PreAuthorize("hasRole('sys-role-del')")
    @AOLog
    public JSONResult adminRoleDeleteV1(@PathVariable String id) {
        adminRoleService.removeById(Ints.tryParse(id));
        return JSONResult.ok();
    }

    @ApiOperation(value = "批量删除角色", notes = "批量删除角色")
    @DeleteMapping("v1/sys-role/batch-del")
    @PreAuthorize("hasRole('sys-role-del')")
    @AOLog
    public JSONResult adminRoleBatchDelV1(@RequestParam("ids") List<Integer> ids) {
        for (Integer id : ids) {
            adminRoleService.removeById(id);
        }
        return JSONResult.ok();
    }

    @Autowired
    AdminOptionLogService logService;

    @ApiOperation(value = "操作审计", notes = "操作审计")
    @GetMapping("v1/sys-user/option-log/list")
    @PreAuthorize("hasRole('sys-user-option-log-query')")
    public JSONResult<Page<AdminOptionLog>> userOptionLogListV1(@Valid PageParam page,
                                                                AdminOptionLogListParam param) {
        AdminOptionLogListQueryParam queryParam = new AdminOptionLogListQueryParam();
        if(param.getStatus() != null && param.getStatus().size() != 0){
            queryParam.setStatus(param.getStatus().get(0));
        }
        queryParam.setUsername(param.getUsername());
        queryParam.setInfo(param.getInfo());

        return JSONResult.ok(jpaPageToPage(
                logService.page(page, queryParam),
                page
        ));
    }

    @ApiOperation(value = "操作审计详情", notes = "操作审计详情")
    @GetMapping("v1/sys-user/option-log/{id}")
    @PreAuthorize("hasRole('sys-user-option-log-query')")
    public JSONResult<AdminOptionLog> userOptionLogDetailV1(@PathVariable Long id) {
        return JSONResult.ok(logService.accessObject(id));
    }


    @Component
    class AdminUserRoleDict implements CmsDict {

        @Autowired
        AdminRoleService adminRoleService;

        @Override
        public String dictKey() {
            return OPTION_ROLE_LIST;
        }

        @Override
        public List<CmsDictEntry> dictEntry(String... childKeys) {
            List<CmsDictEntry> entries = Lists.newArrayList();
            PageParam pageParam = new PageParam();
            pageParam.setPageIndex(1);
            pageParam.setPageSize(9999);
            for (AdminRole adminRole : adminRoleService.page(pageParam, null)) {
                entries.add(new CmsDictEntry(adminRole.getId().toString(), adminRole.getName()));
            }
            return entries;
        }

    }

}
