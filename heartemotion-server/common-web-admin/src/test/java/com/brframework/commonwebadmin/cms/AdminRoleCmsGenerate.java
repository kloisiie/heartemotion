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
public class AdminRoleCmsGenerate {

    private final static String server = "https://dev.api.beiru168.com/gragas";
    private final static String username = "admin";
    private final static String password = "123456";
    private final static Class cla = AdminUserRoleController.class;
    private final static String queryPageMethod = "adminRoleListV1";
    private final static String detailsMethod = "adminRoleGetV1";
    private final static String addMethod = "adminRoleCreateV1";
    private final static String updateMethod = "adminRoleEditV1";
    private final static String deleteMethod = "adminRoleDeleteV1";
    private final static String batchDeleteMethod = "adminRoleBatchDelV1";

    @Test
    public void printMethodParamColumn(){
        // TODO: 2020/12/9 打印只是减少一部分工作量，但实际上打印的内容大多数情况下还是需要编辑的
        Method method = CmsUtils.findMethodByName(cla, updateMethod);
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

        //操作栏
        List<ButtonEntry> actionButtons = Lists.newArrayList();
        actionButtons.add(CmsUtils.tupleToActionButtonEntry(server,
                new Tuple4<>(methodAdd, GenerateGlobals.ProtocolType.route, "新增", Lists.newArrayList()
        )));
        actionButtons.add(CmsUtils.tupleToActionButtonEntry(server,
                new Tuple4<>(methodBatchDelete, GenerateGlobals.ProtocolType.request, "批量删除",
                        Lists.newArrayList(new Tuple2<>("ids", "#{ids}"))
        )));



        //搜索栏
        List<GridLayoutEntry> searchGrids = Lists.newArrayList();


        //数据栏
        List<TableColumnEntry> tableColumns = Lists.newArrayList(
                TableColumnEntry.builder().label("id").prop("id").columnType(GenerateGlobals.TableColumnType.text).width("50").build(),
                TableColumnEntry.builder().label("创建时间").prop("createDate").columnType(GenerateGlobals.TableColumnType.text).width("").build(),
                TableColumnEntry.builder().label("角色名称").prop("name").columnType(GenerateGlobals.TableColumnType.text).width("").build()
        );

        //数据操作栏
        List<ButtonEntry> tableOperationActionButtons = Lists.newArrayList();
        tableOperationActionButtons.add(CmsUtils.tupleToOpartionButtonEntry(server,
                new Tuple4<>(methodUpdate, GenerateGlobals.ProtocolType.route, "编辑", Lists.newArrayList())
        ));
        tableOperationActionButtons.add(CmsUtils.tupleToOpartionButtonEntry(server,
                new Tuple4<>(methodDelete, GenerateGlobals.ProtocolType.request, "删除", Lists.newArrayList(new Tuple2<>("id", "#{id}")))
        ));

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

        CmsPage page = StandardUtils.standardFormPage(methodUpdate, formColumns(), Lists.newArrayList(
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

        CmsPage page = StandardUtils.standardFormPage(methodUpdate, formColumns(), Lists.newArrayList());
        String token = PostSystem.getToken(server, username, password);
        PostSystem.postVersion(server, token, methodUpdate.getName(), JSON.toJSONString(page));
    }

    /**
     * 添加与修改的页面布局是一样的，可以采取这种方式
     * @return
     */
    private List<LayoutEntry> formColumns(){
        List<LayoutEntry> formColumns = Lists.newArrayList();

        formColumns.add(HeaderLayoutEntry.builder().label("角色信息").build());
        formColumns.add(
                GridLayoutUtils.newGridLayout(3, Lists.newArrayList(
                        InputColumnEntry.builder().label("角色名称").prop("name").build(),
                        CascaderColumnEntry.builder()
                                .label("拥有权限")
                                .prop("permissions")
                                .level(3)
                                .multiple(true)
                                .dynamicUrl(CmsUtils.dictToRequest(AdminCmsMenuController.OPTION_MENU_FUN))
                                .build()
                ))
        );

        return formColumns;
    }
}
