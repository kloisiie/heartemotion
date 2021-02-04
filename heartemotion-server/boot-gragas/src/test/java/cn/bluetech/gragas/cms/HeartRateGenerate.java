package cn.bluetech.gragas.cms;

import cn.bluetech.gragas.web.admin.AdminArithmeticController;
import cn.bluetech.gragas.web.admin.AdminHeartRateController;
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
import com.google.common.collect.Maps;
import io.vavr.Tuple4;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * @author xu
 * @date 2020/12/18 13:37
 */
@RunWith(value = JUnit4.class)
public class HeartRateGenerate {

    private final static String server = "https://dev.api.beiru168.com/gragas";
    private final static String username = "admin";
    private final static String password = "123456";
    private final static Class cla = AdminHeartRateController.class;
    private final static String queryPageMethod = "heartRatePageV1";
    private final static String batchDeleteMethod = "heartRateBatchDeleteV1";
    private final static String exportMethod = "heartRateExportV1";

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
    public void list(){
        Method methodPage = CmsUtils.findMethodByName(cla, queryPageMethod);
        Method methodBatchDelete = CmsUtils.findMethodByName(cla, batchDeleteMethod);
        Method methodExport = CmsUtils.findMethodByName(cla, exportMethod);

        Map<String, String> idsParams = Maps.newHashMap();
        idsParams.put("ids", "#{ids}");

        //操作栏
        List<ButtonEntry> actionButtons = Lists.newArrayList();
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
                        .url("request://-X GET --alert \"您确定进行该操作吗？\" #{search._} --export --export-name \"label-export.json\" /admin/access/v1/hr/heart-rate/export")
                        .label("导出")
                        .icon("el-icon-plus")
                        .btnStyle(GenerateGlobals.ButtonStyle.primary)
                        .isCircle(false)
                        .selection(false)
                        .condition("")
                        .plain(true)
                        .btnType(GenerateGlobals.ButtonType.button)
                        .role(ControllerUtil.getRoleByMethod(methodBatchDelete))
                        .build()
        );

        //搜索栏
        List<GridLayoutEntry> searchGrids = Lists.newArrayList(
                GridLayoutUtils.newGridLayout(3, Lists.newArrayList(
                        DateTimePickerColumnEntry.builder().label("心率时间").isRange(true).prop("hrTime").build(),
                        InputColumnEntry.builder().label("设备标识").prop("deviceId").build(),
                        InputColumnEntry.builder().label("佩戴人").prop("wearer").build(),
                        SelectColumnEntry.builder().label("标注状态").prop("labelStatus")
                                .options(Lists.newArrayList(
                                        new Option("无情绪", "NO_MOOD"),
                                        new Option("平稳", "CALMNESS"),
                                        new Option("烦躁", "AGITATED"),
                                        new Option("高兴", "HAPPY")
                                ))
                                .build(),
                        SelectColumnEntry.builder().label("标注类型").prop("labelType")
                                .options(Lists.newArrayList(
                                        new Option("手动标注", "MANUAL"),
                                        new Option("算法标注", "ARITHMETIC")
                                ))
                                .build()
                ))
        );


        //数据栏
        List<TableColumnEntry> tableColumns = Lists.newArrayList(
                TableColumnEntry.builder().label("ID").prop("id").columnType(GenerateGlobals.TableColumnType.text).width("50").build(),
                TableColumnEntry.builder().label("产生时间").prop("hrTime").columnType(GenerateGlobals.TableColumnType.text).width("").build(),
                TableColumnEntry.builder().label("设备标识").prop("deviceId").columnType(GenerateGlobals.TableColumnType.text).width("").build(),
                TableColumnEntry.builder().label("佩戴人").prop("wearer").columnType(GenerateGlobals.TableColumnType.text).width("").build(),
                TableColumnEntry.builder().label("心率值").prop("hrValue").columnType(GenerateGlobals.TableColumnType.text).width("70").build(),
                TableColumnEntry.builder().label("标注状态").prop("labelStatusName").columnType(GenerateGlobals.TableColumnType.text).width("").build(),
                TableColumnEntry.builder().label("应对手段").prop("means").columnType(GenerateGlobals.TableColumnType.text).width("").build(),
                TableColumnEntry.builder().label("标注类型").prop("labelTypeName").columnType(GenerateGlobals.TableColumnType.text).width("").build()
        );

        //数据操作栏
        List<ButtonEntry> tableOperationActionButtons = Lists.newArrayList();


        /*---------------------------- 基础架构-一般情况下不用动 ------------------------------- */
        CmsPage page = StandardUtils.standardListPage(methodPage, actionButtons,
                searchGrids, tableOperationActionButtons, tableColumns, true, true);

        String token = PostSystem.getToken(server, username, password);
        PostSystem.postVersion(server, token, methodPage.getName(), JSON.toJSONString(page));
    }
}
