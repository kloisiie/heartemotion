package com.brframework.commonwebadmin.web.admin;

import com.brframework.commonsecurity.core.SecurityContextHolder;
import com.brframework.commonweb.json.JSONResult;
import com.brframework.commonwebadmin.aop.annotation.AOLog;
import com.brframework.commonwebadmin.json.admin.adminuser.AdminMenu;
import com.brframework.commonwebadmin.json.admin.adminuser.LoginParam;
import com.brframework.commonwebadmin.json.admin.adminuser.LoginResult;
import com.brframework.commonwebadmin.service.admin.AdminUserService;
import com.brframework.commonwebadmin.service.admin.FunctionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author xu
 * @date 2018/3/9 0009 下午 9:04
 * 后台管理
 */
@RestController
@Api(tags = "后台管理")
@RequestMapping("/admin/")
public class AdminController {

    @Autowired
    AdminUserService adminUserService;
    @Autowired
    FunctionService functionService;

    @ApiOperation(value = "管理系统登录", notes = "登录")
    @PostMapping("v1/login")
    @AOLog
    public JSONResult<LoginResult> loginV1(@Valid LoginParam param) {
        return JSONResult.ok(LoginResult.builder()
                .token(adminUserService.login(param.getUsername(), param.getPassword()))
                .build());
    }

    @ApiOperation(value = "获取用户角色所有功能清单", notes = "获取用户角色所有功能清单")
    @GetMapping("access/v1/function/role")
    public JSONResult<List<AdminMenu>> functionListRoleV1() {

        return JSONResult.ok(functionService.getRoleMenus(
                SecurityContextHolder.getUserDetails().getAuthorities()));
    }


}
