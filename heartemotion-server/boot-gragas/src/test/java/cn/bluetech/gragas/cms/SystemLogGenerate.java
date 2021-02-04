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
public class SystemLogGenerate {

    private final static String server = "https://dev.api.beiru168.com/gragas";
    private final static String username = "admin";
    private final static String password = "123456";
    private final static Class cla = AdminSystemMonitorController.class;
    private final static String queryPageMethod = "systemLogList";
    private final static String detailsMethod = "systemLogDetails";

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
                        InputColumnEntry.builder().label("搜索").prop("search").build(),
                        SelectColumnEntry.builder().label("Level").prop("level").options(Lists.newArrayList(
                                new Option("DEBUG", "DEBUG"),
                                new Option("INFO", "INFO"),
                                new Option("WARN", "WARN"),
                                new Option("ERROR", "ERROR")
                        )).build(),
                        InputColumnEntry.builder().label("threadName").prop("threadName").build(),
                        InputColumnEntry.builder().label("loggerName").prop("loggerName").build(),
                        DateTimePickerColumnEntry.builder().label("时间区间").prop("timeBetween").isRange(true).build()
                ))
        );


        //数据栏
        List<TableColumnEntry> tableColumns = Lists.newArrayList(
                TableColumnEntry.builder().label("TimeStamp").prop("timeStampTime").columnType(GenerateGlobals.TableColumnType.text).width("").build(),
                TableColumnEntry.builder().label("ThreadName").prop("threadName").columnType(GenerateGlobals.TableColumnType.text).width("").build(),
                TableColumnEntry.builder().label("LoggerName").prop("loggerName").columnType(GenerateGlobals.TableColumnType.text).width("").build(),
                TableColumnEntry.builder().label("Level").prop("level").columnType(GenerateGlobals.TableColumnType.text).width("").build(),
                TableColumnEntry.builder().label("IP").prop("ip").columnType(GenerateGlobals.TableColumnType.text).width("").build(),
                TableColumnEntry.builder().label("Synopsis").prop("synopsis").columnType(GenerateGlobals.TableColumnType.text).width("").build()
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
                        InputColumnEntry.builder().label("timeStamp").prop("timeStampTime").build(),
                        InputColumnEntry.builder().label("threadName").prop("threadName").build(),
                        InputColumnEntry.builder().label("loggerName").prop("loggerName").build(),
                        InputColumnEntry.builder().label("level").prop("level").build(),
                        InputColumnEntry.builder().label("ip").prop("ip").build(),
                        InputColumnEntry.builder().label("synopsis").prop("synopsis").build()
                ))
        );

        formColumns.add(
                GridLayoutUtils.newGridLayout(1, Lists.newArrayList(
                        RichTextColumnEntry.builder().label("message").prop("message").build()
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
