package com.brframework.commonwebadmin.cms;

import com.alibaba.fastjson.JSON;
import com.brframework.cms2.generate.*;
import com.brframework.cms2.generate.column.CascaderColumnEntry;
import com.brframework.cms2.generate.column.InputColumnEntry;
import com.brframework.cms2.generate.column.SelectColumnEntry;
import com.brframework.cms2.generate.column.TableColumnEntry;
import com.brframework.cms2.generate.layout.GridLayoutEntry;
import com.brframework.cms2.generate.layout.HeaderLayoutEntry;
import com.brframework.cms2.generate.layout.LayoutEntry;
import com.brframework.cms2.generate.utils.CmsUtils;
import com.brframework.cms2.generate.utils.GridLayoutUtils;
import com.brframework.cms2.generate.utils.StandardUtils;
import com.brframework.cms2.utils.ControllerUtil;
import com.brframework.cms2.web.AdminCmsMenuController;
import com.brframework.commonwebadmin.web.admin.AdminUserRoleController;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.vavr.Tuple2;
import io.vavr.Tuple4;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * @author xu
 * @date 2020/12/9 11:47
 */
@RunWith(value = JUnit4.class)
public class AdminUserCmsGenerate {

    private final static String server = "https://dev.api.beiru168.com/gragas";
    private final static String username = "admin";
    private final static String password = "123456";
    private final static Class cla = AdminUserRoleController.class;
    private final static String queryPageMethod = "adminUserListV1";
    private final static String detailsMethod = "adminUserInfo";
    private final static String addMethod = "adminUserCreateV1";
    private final static String updateMethod = "adminUserEditV1";
    private final static String deleteMethod = "adminUserDeleteV1";
    private final static String batchDeleteMethod = "adminUserBatchDeleteV1";
    private final static String restPasswordMethod = "adminUserEditPasswordV1";

    @Test
    public void printMethodParamColumn(){
        // TODO: 2020/12/9 打印只是减少一部分工作量，但实际上打印的内容大多数情况下还是需要编辑的
        Method method = CmsUtils.findMethodByName(cla, addMethod);
        CmsUtils.printMethodParamColumn(method);
    }

    @Test
    public void printMethodPageResultTableColumn(){
        // TODO: 2020/12/9 打印只是减少一部分工作量，但实际上打印的内容大多数情况下还是需要编辑的
        Method method = CmsUtils.findMethodByName(cla, queryPageMethod);
        CmsUtils.printMethodPageResultTableColumn(method);
    }


    @Test
    public void list(){
        Method methodPage = CmsUtils.findMethodByName(cla, queryPageMethod);
        Method methodAdd = CmsUtils.findMethodByName(cla, addMethod);
        Method methodBatchDelete = CmsUtils.findMethodByName(cla, batchDeleteMethod);
        Method methodUpdate = CmsUtils.findMethodByName(cla, updateMethod);
        Method methodDelete = CmsUtils.findMethodByName(cla, deleteMethod);
        Method methodRestPassword= CmsUtils.findMethodByName(cla, restPasswordMethod);

        Map<String, String> idsParams = Maps.newHashMap();
        idsParams.put("ids", "#{ids}");

        //操作栏
        List<ButtonEntry> actionButtons = Lists.newArrayList();
        actionButtons.add(
                ButtonEntry.builder()
                        .url(PostSystem.getRouteInfo(server, methodAdd.getName()).getString("route"))
                        .label("新增")
                        .icon("el-icon-plus")
                        .btnStyle(GenerateGlobals.ButtonStyle.primary)
                        .isCircle(false)
                        .selection(false)
                        .condition("")
                        .plain(true)
                        .btnType(GenerateGlobals.ButtonType.button)
                        .role(ControllerUtil.getRoleByMethod(methodAdd))
                        .build()
        );
        actionButtons.add(
                ButtonEntry.builder()
                        .url(CmsUtils.methodToRequest(methodBatchDelete, idsParams))
                        .label("批量删除")
                        .icon("el-icon-delete")
                        .btnStyle(GenerateGlobals.ButtonStyle.danger)
                        .isCircle(false)
                        .selection(true)
                        .condition("")
                        .plain(true)
                        .btnType(GenerateGlobals.ButtonType.button)
                        .role(ControllerUtil.getRoleByMethod(methodBatchDelete))
                        .build()
        );
        actionButtons.add(
                ButtonEntry.builder()
                        .url(CmsUtils.methodToRequest(
                                CmsUtils.findMethodByName(cla, "adminUserBatchDisableV1"),
                                idsParams))
                        .label("批量禁用")
                        .icon("el-icon-magic-stick")
                        .btnStyle(GenerateGlobals.ButtonStyle.info)
                        .isCircle(false)
                        .selection(true)
                        .condition("")
                        .plain(true)
                        .btnType(GenerateGlobals.ButtonType.button)
                        .role(ControllerUtil.getRoleByMethod(CmsUtils.findMethodByName(cla, "adminUserBatchDisableV1")))
                        .build()
        );
        actionButtons.add(
                ButtonEntry.builder()
                        .url(CmsUtils.methodToRequest(
                                CmsUtils.findMethodByName(cla, "adminUserBatchEnableV1"),
                                idsParams))
                        .label("批量启用")
                        .icon("el-icon-tickets")
                        .btnStyle(GenerateGlobals.ButtonStyle.success)
                        .isCircle(false)
                        .selection(true)
                        .condition("")
                        .plain(true)
                        .btnType(GenerateGlobals.ButtonType.button)
                        .role(ControllerUtil.getRoleByMethod(CmsUtils.findMethodByName(cla, "adminUserBatchEnableV1")))
                        .build()
        );

        //搜索栏
        List<GridLayoutEntry> searchGrids = Lists.newArrayList();


        //数据栏
        List<TableColumnEntry> tableColumns = Lists.newArrayList(
                TableColumnEntry.builder().label("id").prop("id").columnType(GenerateGlobals.TableColumnType.text).width("50").build(),
                TableColumnEntry.builder().label("注册时间").prop("createDate").columnType(GenerateGlobals.TableColumnType.text).width("").build(),
                TableColumnEntry.builder().label("登录名").prop("username").columnType(GenerateGlobals.TableColumnType.text).width("").build(),
                TableColumnEntry.builder().label("昵称").prop("nickname").columnType(GenerateGlobals.TableColumnType.text).width("").build(),
                TableColumnEntry.builder().label("账号状态").prop("stateName").columnType(GenerateGlobals.TableColumnType.text).width("100").build(),
                TableColumnEntry.builder().label("拥有角色").prop("roles").columnType(GenerateGlobals.TableColumnType.text).width("").build()
        );

        //数据操作栏
        List<ButtonEntry> tableOperationActionButtons = Lists.newArrayList();
        tableOperationActionButtons.add(ButtonEntry.builder()
                .url(PostSystem.getRouteInfo(server, methodUpdate.getName()).getString("route"))
                .label("编辑")
                .icon("el-icon-edit-outline")
                .btnStyle(GenerateGlobals.ButtonStyle.primary)
                .condition("")
                .role(ControllerUtil.getRoleByMethod(methodUpdate))
                .build());
        tableOperationActionButtons.add(ButtonEntry.builder()
                .url(CmsUtils.methodToRequest(methodDelete))
                .label("删除")
                .icon("el-icon-delete")
                .btnStyle(GenerateGlobals.ButtonStyle.danger)
                .condition("")
                .role(ControllerUtil.getRoleByMethod(methodDelete))
                .build());
        tableOperationActionButtons.add(ButtonEntry.builder()
                .url(CmsUtils.methodToRequest(
                        CmsUtils.findMethodByName(cla, "adminUserDisableV1")))
                .label("禁用")
                .icon("el-icon-magic-stick")
                .btnStyle(GenerateGlobals.ButtonStyle.info)
                .condition("#{state} == 1")
                .role(ControllerUtil.getRoleByMethod(CmsUtils.findMethodByName(cla, "adminUserDisableV1")))
                .build());
        tableOperationActionButtons.add(ButtonEntry.builder()
                .url(CmsUtils.methodToRequest(
                        CmsUtils.findMethodByName(cla, "adminUserEnableV1")))
                .label("启用")
                .icon("el-icon-tickets")
                .btnStyle(GenerateGlobals.ButtonStyle.success)
                .condition("#{state} == 0")
                .role(ControllerUtil.getRoleByMethod(CmsUtils.findMethodByName(cla, "adminUserEnableV1")))
                .build());
        tableOperationActionButtons.add(ButtonEntry.builder()
                .url(PostSystem.getRouteInfo(server, methodRestPassword.getName()).getString("route"))
                .label("密码重置")
                .icon("el-icon-tickets")
                .btnStyle(GenerateGlobals.ButtonStyle.danger)
                .role(ControllerUtil.getRoleByMethod(CmsUtils.findMethodByName(cla, "adminUserEditPasswordV1")))
                .build());

        /*---------------------------- 基础架构-一般情况下不用动 ------------------------------- */
        CmsPage page = StandardUtils.standardListPage(methodPage, actionButtons,
                searchGrids, tableOperationActionButtons, tableColumns);

        String token = PostSystem.getToken(server, username, password);
        PostSystem.postVersion(server, token, methodPage.getName(), JSON.toJSONString(page));
    }

    @Test
    public void formUpdate(){
        Method methodUpdate = CmsUtils.findMethodByName(cla, updateMethod);
        Method methodDetails = CmsUtils.findMethodByName(cla, detailsMethod);

        List<LayoutEntry> formColumns = Lists.newArrayList();

        formColumns.add(HeaderLayoutEntry.builder().label("基本信息").build());
        formColumns.add(
                GridLayoutUtils.newGridLayout(2, Lists.newArrayList(
                        InputColumnEntry.builder().label("登录名").prop("username").minLength(1).maxLength(18).build(),
                        InputColumnEntry.builder().label("昵称").prop("nickname").minLength(1).maxLength(18).build()
                ))
        );
        formColumns.add(HeaderLayoutEntry.builder().label("角色分配").build());
        formColumns.add(
                GridLayoutUtils.newGridLayout(1, Lists.newArrayList(
                        CascaderColumnEntry.builder()
                                .label("用户角色")
                                .prop("roles")
                                .level(1)
                                .multiple(true)
                                .dynamic(true)
                                .dynamicUrl(CmsUtils.dictToRequest(AdminUserRoleController.OPTION_ROLE_LIST))
                                .build()
                ))
        );

        CmsPage page = StandardUtils.standardFormPage(methodUpdate, formColumns, Lists.newArrayList(
                RequestProtocol.builder()
                        .method(ControllerUtil.getHttpMethod(methodDetails).toUpperCase())
                        .url(CmsUtils.methodToCmsUrl(methodDetails))
                        .build()
        ));

        String token = PostSystem.getToken(server, username, password);
        PostSystem.postVersion(server, token, methodUpdate.getName(), JSON.toJSONString(page));
    }



    @Test
    public void formAdd(){
        Method methodUpdate = CmsUtils.findMethodByName(cla, addMethod);

        List<LayoutEntry> formColumns = Lists.newArrayList();

        formColumns.add(HeaderLayoutEntry.builder().label("角色信息").build());
        formColumns.add(
                GridLayoutUtils.newGridLayout(3, Lists.newArrayList(
                        InputColumnEntry.builder().label("登录名").prop("username").minLength(1).maxLength(18).build(),
                        InputColumnEntry.builder().label("昵称").prop("nickname").minLength(1).maxLength(18).build(),
                        InputColumnEntry.builder().label("密码").prop("password").minLength(1).maxLength(18).build()
                ))
        );
        formColumns.add(HeaderLayoutEntry.builder().label("角色分配").build());
        formColumns.add(
                GridLayoutUtils.newGridLayout(1, Lists.newArrayList(
                        CascaderColumnEntry.builder()
                                .label("用户角色")
                                .prop("roles")
                                .level(1)
                                .multiple(true)
                                .dynamic(true)
                                .dynamicUrl(CmsUtils.dictToRequest(AdminUserRoleController.OPTION_ROLE_LIST))
                                .build()
                ))
        );


        CmsPage page = StandardUtils.standardFormPage(methodUpdate, formColumns, Lists.newArrayList());
        String token = PostSystem.getToken(server, username, password);
        PostSystem.postVersion(server, token, methodUpdate.getName(), JSON.toJSONString(page));
    }


    @Test
    public void formRestPassword(){
        Method methodRestPassword = CmsUtils.findMethodByName(cla, restPasswordMethod);
        Method methodDetails = CmsUtils.findMethodByName(cla, detailsMethod);

        List<LayoutEntry> formColumns = Lists.newArrayList();

        formColumns.add(HeaderLayoutEntry.builder().label("重置密码").build());
        formColumns.add(
                GridLayoutUtils.newGridLayout(2, Lists.newArrayList(
                        InputColumnEntry.builder().label("用户名").prop("nickname").disabled(true).build(),
                        InputColumnEntry.builder().label("新密码").prop("password").required(true).minLength(1).maxLength(18).build()
                ))
        );

        CmsPage page = StandardUtils.standardFormPage(methodRestPassword, formColumns, Lists.newArrayList(
                RequestProtocol.builder()
                        .method(ControllerUtil.getHttpMethod(methodDetails).toUpperCase())
                        .url(CmsUtils.methodToCmsUrl(methodDetails))
                        .build()
        ));
        String token = PostSystem.getToken(server, username, password);
        PostSystem.postVersion(server, token, methodRestPassword.getName(), JSON.toJSONString(page));
    }

}
