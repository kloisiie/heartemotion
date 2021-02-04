package cn.bluetech.gragas.cms;

import cn.bluetech.gragas.web.admin.AdminSystemMonitorController;
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
import com.google.common.collect.Lists;
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
public class MQLogGenerate {

    private final static String server = "https://dev.api.beiru168.com/gragas";
    private final static String username = "admin";
    private final static String password = "123456";
    private final static Class cla = AdminSystemMonitorController.class;
    private final static String queryPageMethod = "systemMqMessageList";
    private final static String detailsMethod = "systemMqMessageGet";

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
                                new Option("等待", "AWAIT"),
                                new Option("运行中", "RUNNING"),
                                new Option("失败", "DEFEATED"),
                                new Option("成功", "SUCCESS")
                        )).build(),
                        InputColumnEntry.builder().label("topic").prop("topic").build(),
                        InputColumnEntry.builder().label("消息ID").prop("messageId").build(),
                        DateTimePickerColumnEntry.builder().label("创建时间区间").prop("pushTimeBetween").isRange(true).build(),
                        DateTimePickerColumnEntry.builder().label("执行时间区间").prop("runningTimeBetween").isRange(true).build()
                ))
        );


        //数据栏
        List<TableColumnEntry> tableColumns = Lists.newArrayList(
                TableColumnEntry.builder().label("消息ID").prop("messageId").columnType(GenerateGlobals.TableColumnType.text).width("150").build(),
                TableColumnEntry.builder().label("TOPIC").prop("topic").columnType(GenerateGlobals.TableColumnType.text).width("200").build(),
                TableColumnEntry.builder().label("携带数据").prop("message").columnType(GenerateGlobals.TableColumnType.text).width("").build(),
                TableColumnEntry.builder().label("发送时间").prop("pushTime").columnType(GenerateGlobals.TableColumnType.text).width("").build(),
                TableColumnEntry.builder().label("延时(s)").prop("delayedSeconds").columnType(GenerateGlobals.TableColumnType.text).width("90").build(),
                TableColumnEntry.builder().label("剩余(s)").prop("delayedRemainSeconds").columnType(GenerateGlobals.TableColumnType.text).width("90").build(),
                TableColumnEntry.builder().label("状态").prop("status").columnType(GenerateGlobals.TableColumnType.text).width("").build(),
                TableColumnEntry.builder().label("开始执行时间").prop("runningStartTime").columnType(GenerateGlobals.TableColumnType.text).width("").build(),
                TableColumnEntry.builder().label("执行结束时间").prop("runningEndTime").columnType(GenerateGlobals.TableColumnType.text).width("").build(),
                TableColumnEntry.builder().label("耗时(ms)").prop("elapsedTime").columnType(GenerateGlobals.TableColumnType.text).width("100").build(),
                TableColumnEntry.builder().label("发送服务器信息").prop("pushInfo").columnType(GenerateGlobals.TableColumnType.text).width("").build(),
                TableColumnEntry.builder().label("执行服务器信息").prop("runningInfo").columnType(GenerateGlobals.TableColumnType.text).width("").build()
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
                        InputColumnEntry.builder().label("消息ID").prop("messageId").build(),
                        InputColumnEntry.builder().label("topic").prop("topic").build(),
                        InputColumnEntry.builder().label("发送时间").prop("pushTime").build(),
                        InputColumnEntry.builder().label("延时(s)").prop("delayedSeconds").build(),
                        InputColumnEntry.builder().label("剩余(s)").prop("delayedRemainSeconds").build(),
                        SelectColumnEntry.builder().label("状态").prop("status").options(Lists.newArrayList(
                                new Option("等待", "AWAIT"),
                                new Option("运行中", "RUNNING"),
                                new Option("失败", "DEFEATED"),
                                new Option("成功", "SUCCESS")
                        )).build(),
                        InputColumnEntry.builder().label("开始执行时间").prop("runningStartTime").build(),
                        InputColumnEntry.builder().label("执行结束时间").prop("runningEndTime").build(),
                        InputColumnEntry.builder().label("耗时(ms)").prop("elapsedTime").build(),
                        InputColumnEntry.builder().label("发送服务器信息").prop("pushInfo").build(),
                        InputColumnEntry.builder().label("执行服务器信息").prop("runningInfo").build()
                ))
        );

        formColumns.add(
                GridLayoutUtils.newGridLayout(1, Lists.newArrayList(
                        TextareaColumnEntry.builder().label("携带数据").prop("message").build(),
                        RichTextColumnEntry.builder().label("失败信息").prop("defeatedInfo").build()
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
