package com.brframework.cms2.generate.utils;

import com.alibaba.fastjson.JSON;
import com.brframework.cms2.generate.*;
import com.brframework.cms2.generate.column.TableColumnEntry;
import com.brframework.cms2.generate.column.TableOperationColumnEntry;
import com.brframework.cms2.generate.layout.*;
import com.brframework.cms2.utils.ControllerUtil;
import com.google.common.collect.Lists;

import java.lang.reflect.Method;
import java.util.List;

/**
 * 标准工具
 * @author xu
 * @date 2020/12/11 10:04
 */
public class StandardUtils {
    public static CmsPage standardListPage(Method method, List<ButtonEntry> actionButtons,
                                           List<GridLayoutEntry> searchGrids,
                                           List<ButtonEntry> tableOperationActionButtons,
                                           List<TableColumnEntry> tableColumns){
        return standardListPage(method, actionButtons, searchGrids, tableOperationActionButtons, tableColumns,
                true, true);
    }


    public static CmsPage standardListPage(Method method, List<ButtonEntry> actionButtons,
                                           List<GridLayoutEntry> searchGrids,
                                           List<ButtonEntry> tableOperationActionButtons,
                                           List<TableColumnEntry> tableColumns, boolean pagination,
                                           boolean selection){
        ActionLayoutEntry actionLayout = ActionLayoutEntry.builder()
                .columns(actionButtons)
                .build();

        SearchLayoutEntry searchLayout = SearchLayoutEntry.builder()
                .columns(searchGrids)
                .build();

        TableOperationColumnEntry tableOperationColumn = TableOperationColumnEntry.builder()
                .columns(tableOperationActionButtons)
                .build();
        TableLayoutEntry tableLayout = TableLayoutEntry.builder()
                .columns(tableColumns)
                .operation(tableOperationColumn)
                .height(500)
                .pagination(pagination)
                .selection(selection)
                .build();

        ListLayoutEntry listLayout = ListLayoutEntry.builder()
                .actionLayout(actionLayout)
                .searchLayout(searchLayout)
                .tableLayout(tableLayout)
                .build();

        String searchParam = "";
        if(searchGrids != null && searchGrids.size() != 0){
            searchParam = " #{search._}";
        }

        //上下文配置
        CmsConfig config = CmsConfig.builder()
                .context(Lists.newArrayList(
                        "request://-X GET" + searchParam + " --context " +
                                "table=data.list&pageIndex=data.pageIndex&pageSize=data.pageSize&total=data.total " +
                                CmsUtils.methodToCmsUrl(method)
                ))
                .build();

        return CmsPage.builder()
                .page(Lists.newArrayList(listLayout))
                .config(config)
                .build();
    }


    public static ActionLayoutEntry standardActionLayout(Method method){
        return ActionLayoutEntry.builder()
                .columns(Lists.newArrayList(
                        ButtonEntry.builder().label("返回").icon("el-icon-back").url("action://back")
                                .btnStyle(GenerateGlobals.ButtonStyle.ghost)
                                .btnType(GenerateGlobals.ButtonType.button)
                                .build(),
                        ButtonEntry.builder().label("提交").icon("el-icon-top").valid(true)
                                .url(RequestProtocol.builder()
                                        .method(ControllerUtil.getHttpMethod(method).toUpperCase())
                                        .alert("您确定修改吗？")
                                        .success("提交成功！")
                                        .back(true)
                                        .formParam(true)
                                        .url(CmsUtils.methodToCmsUrl(method))
                                        .build())
                                .btnStyle(GenerateGlobals.ButtonStyle.primary)
                                .btnType(GenerateGlobals.ButtonType.button)
                                .build(),
                        ButtonEntry.builder().label("清空").icon("el-icon-delete").url("action://clear")
                                .btnStyle(GenerateGlobals.ButtonStyle.danger)
                                .btnType(GenerateGlobals.ButtonType.button)
                                .build()
                ))
                .build();
    }

    public static ActionLayoutEntry backActionLayout(){
        return ActionLayoutEntry.builder()
                .columns(Lists.newArrayList(
                        ButtonEntry.builder().label("返回").icon("el-icon-back").url("action://back")
                                .btnStyle(GenerateGlobals.ButtonStyle.ghost)
                                .btnType(GenerateGlobals.ButtonType.button)
                                .build()
                ))
                .build();
    }

    public static CmsPage standardFormPage(Method method, List<LayoutEntry> formColumns,
                                           List<String> context){
        return standardFormPage(method, formColumns, context, true);
    }

    public static CmsPage standardFormPage(Method method, List<LayoutEntry> formColumns,
                                           List<String> context, boolean editable){
        //没有method传入时，没有操作
        if(method != null){
            formColumns.add(0, standardActionLayout(method));
        } else {
            formColumns.add(0, backActionLayout());
        }

        FormLayoutEntry formLayout = FormLayoutEntry.builder()
                .columns(formColumns)
                .editable(editable)
                .build();

        return CmsPage.builder()
                .page(Lists.newArrayList(formLayout))
                .config(CmsConfig.builder().context(context).build())
                .build();


    }

}
