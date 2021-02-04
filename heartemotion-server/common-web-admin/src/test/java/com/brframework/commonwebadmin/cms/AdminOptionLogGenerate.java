package com.brframework.commonwebadmin.cms;

import com.alibaba.fastjson.JSON;
import com.brframework.cms2.generate.*;
import com.brframework.cms2.generate.column.*;
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
import io.vavr.Tuple2;
import io.vavr.Tuple4;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @author xu
 * @date 2020/12/9 11:47
 */
@RunWith(value = JUnit4.class)
public class AdminOptionLogGenerate {

    private final static String server = "https://dev.api.beiru168.com/gragas";
    private final static String username = "admin";
    private final static String password = "123456";
    private final static Class cla = AdminUserRoleController.class;
    private final static String queryPageMethod = "userOptionLogListV1";
    private final static String detailsMethod = "userOptionLogDetailV1";

    @Test
    public void printMethodParamColumn(){
        // TODO: 2020/12/9 打印只是减少一部分工作量，但实际上打印的内容大多数情况下还是需要编辑的
        Method method = CmsUtils.findMethodByName(cla, queryPageMethod);
        CmsUtils.printMethodParamColumn(method);
    }

    @Test
    public void printMethodPageResultTableColumn(){
        // TODO: 2020/12/9 打印只是减少一部分工作量，但实际上打印的内容大多数情况下还是需要编辑的
        Method method = CmsUtils.findMethodByName(cla, queryPageMethod);
        CmsUtils.printMethodPageResultTableColumn(method);
    }

    @Test
    public void printMethodResultColumn(){
        // TODO: 2020/12/9 打印只是减少一部分工作量，但实际上打印的内容大多数情况下还是需要编辑的
        Method method = CmsUtils.findMethodByName(cla, detailsMethod);
        CmsUtils.printMethodResultColumn(method);
    }



    @Test
    public void list(){
        Method methodPage = CmsUtils.findMethodByName(cla, queryPageMethod);
        Method methodDetails = CmsUtils.findMethodByName(cla, detailsMethod);

        //操作栏
        List<ButtonEntry> actionButtons = Lists.newArrayList();


        //搜索栏
        List<GridLayoutEntry> searchGrids = Lists.newArrayList(
                GridLayoutUtils.newGridLayout(3, Lists.newArrayList(
                        SelectColumnEntry.builder().label("状态").prop("status").options(Lists.newArrayList(
                                new Option("OK", "OK"),
                                new Option("ERROR", "ERROR")
                        )).build(),
                        InputColumnEntry.builder().label("登录名").prop("username").build(),
                        InputColumnEntry.builder().label("操作信息").prop("info").build()
                ))
        );


        //数据栏
        List<TableColumnEntry> tableColumns = Lists.newArrayList(
                TableColumnEntry.builder().label("id").prop("id").columnType(GenerateGlobals.TableColumnType.text).width("50").build(),
                TableColumnEntry.builder().label("操作时间").prop("createDate").columnType(GenerateGlobals.TableColumnType.text).width("").build(),
                TableColumnEntry.builder().label("操作者登录名").prop("username").columnType(GenerateGlobals.TableColumnType.text).width("").build(),
                TableColumnEntry.builder().label("IP").prop("ip").columnType(GenerateGlobals.TableColumnType.text).width("").build(),
                TableColumnEntry.builder().label("状态").prop("status").columnType(GenerateGlobals.TableColumnType.text).width("80").build(),
                TableColumnEntry.builder().label("信息").prop("info").columnType(GenerateGlobals.TableColumnType.text).width("").build(),
                TableColumnEntry.builder().label("触发api").prop("callApi").columnType(GenerateGlobals.TableColumnType.text).width("").build()
        );

        //数据操作栏
        List<ButtonEntry> tableOperationActionButtons = Lists.newArrayList();
        tableOperationActionButtons.add(ButtonEntry.builder()
                .url(PostSystem.getRouteInfo(server, methodDetails.getName()).getString("route"))
                .label("详情")
                .icon("methodDetails")
                .btnStyle(GenerateGlobals.ButtonStyle.primary)
                .condition("")
                .role(ControllerUtil.getRoleByMethod(methodDetails))
                .build());

        /*---------------------------- 基础架构-一般情况下不用动 ------------------------------- */
        CmsPage page = StandardUtils.standardListPage(methodPage, actionButtons,
                searchGrids, tableOperationActionButtons, tableColumns, true, false);

        String token = PostSystem.getToken(server, username, password);
        PostSystem.postVersion(server, token, methodPage.getName(), JSON.toJSONString(page));
    }




    @Test
    public void formDetail(){
        Method methodDetails = CmsUtils.findMethodByName(cla, detailsMethod);
        List<LayoutEntry> formColumns = Lists.newArrayList();

        formColumns.add(HeaderLayoutEntry.builder().label("基本信息").build());
        formColumns.add(
                GridLayoutUtils.newGridLayout(3, Lists.newArrayList(
                        NumberColumnEntry.builder().label("ID").prop("id").build(),
                        InputColumnEntry.builder().label("时间").prop("createDate").build(),
                        InputColumnEntry.builder().label("登录名").prop("username").build(),
                        InputColumnEntry.builder().label("IP").prop("ip").build(),
                        InputColumnEntry.builder().label("状态").prop("status").build(),
                        InputColumnEntry.builder().label("信息").prop("info").build(),
                        InputColumnEntry.builder().label("触发api").prop("callApi").build()
                ))
        );

        formColumns.add(
                GridLayoutUtils.newGridLayout(1, Lists.newArrayList(
                        TextareaColumnEntry.builder().label("参数").prop("param").build(),
                        RichTextColumnEntry.builder().label("错误日志").prop("errorLog").build()
                ))
        );


        CmsPage page = StandardUtils.standardFormPage(null, formColumns, Lists.newArrayList(
                RequestProtocol.builder()
                        .method(ControllerUtil.getHttpMethod(methodDetails).toUpperCase())
                        .url(CmsUtils.methodToCmsUrl(methodDetails))
                        .build()
        ), false);
        String token = PostSystem.getToken(server, username, password);
        PostSystem.postVersion(server, token, methodDetails.getName(), JSON.toJSONString(page));
    }
}
