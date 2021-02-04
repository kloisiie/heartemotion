package cn.bluetech.gragas.cms;

import cn.bluetech.gragas.web.admin.AdminOptionLogController;
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
public class OptionLogGenerate {

    private final static String server = "https://dev.api.beiru168.com/gragas";
    private final static String username = "admin";
    private final static String password = "123456";
    private final static Class cla = AdminOptionLogController.class;
    private final static String queryPageMethod = "optionLogPageV1";
    private final static String detailsMethod = "optionLogAddV1";
    private final static String batchDeleteMethod = "optionLogBatchDeleteV1";

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
        Method methodBatchDelete = CmsUtils.findMethodByName(cla, batchDeleteMethod);

        //操作栏
        List<ButtonEntry> actionButtons = Lists.newArrayList();
        actionButtons.add(CmsUtils.tupleToActionButtonEntry(server,
                new Tuple4<>(methodBatchDelete, GenerateGlobals.ProtocolType.request, "批量删除",
                        org.assertj.core.util.Lists.newArrayList(new Tuple2<>("ids", "#{ids}"))
                )));

        //搜索栏
        List<GridLayoutEntry> searchGrids = Lists.newArrayList(
                GridLayoutUtils.newGridLayout(3, Lists.newArrayList(
                        DateTimePickerColumnEntry.builder().label("创建时间").prop("createDate")
                                .isRange(true)
                                .build(),
                        InputColumnEntry.builder().label("客户端ID").prop("clientId").build(),
                        InputColumnEntry.builder().label("调用接口").prop("callApi").build(),
                        InputColumnEntry.builder().label("IP").prop("ip").build()
                ))
        );


        //数据栏
        List<TableColumnEntry> tableColumns = Lists.newArrayList(
                TableColumnEntry.builder().label("id").prop("id").columnType(GenerateGlobals.TableColumnType.text).width("50").build(),
                TableColumnEntry.builder().label("创建时间").prop("createDate").columnType(GenerateGlobals.TableColumnType.text).width("").build(),
                TableColumnEntry.builder().label("客户端ID").prop("clientId").columnType(GenerateGlobals.TableColumnType.text).width("").build(),
                TableColumnEntry.builder().label("调用接口").prop("callApi").columnType(GenerateGlobals.TableColumnType.text).width("").build(),
                TableColumnEntry.builder().label("IP").prop("ip").columnType(GenerateGlobals.TableColumnType.text).width("").build()
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
