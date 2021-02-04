package cn.bluetech.gragas.cms;

import cn.bluetech.gragas.web.admin.AdminArithmeticController;
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
import io.vavr.Tuple2;
import io.vavr.Tuple4;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @author xu
 * @date 2020/12/18 13:37
 */
@RunWith(value = JUnit4.class)
public class ArithmeticGenerate {

    private final static String server = "https://dev.api.beiru168.com/gragas";
    private final static String username = "admin";
    private final static String password = "123456";
    private final static Class cla = AdminArithmeticController.class;
    private final static String queryPageMethod = "arithmeticPageV1";
    private final static String detailsMethod = "arithmeticDetailsV1";
    private final static String addMethod = "arithmeticAddV1";
    private final static String updateMethod = "arithmeticUpdateV1";
    private final static String deleteMethod = "arithmeticDeleteV1";


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
        Method methodAdd = CmsUtils.findMethodByName(cla, addMethod);
        Method methodUpdate = CmsUtils.findMethodByName(cla, updateMethod);
        Method methodDelete = CmsUtils.findMethodByName(cla, deleteMethod);

        //操作栏
        List<ButtonEntry> actionButtons = Lists.newArrayList();
        actionButtons.add(CmsUtils.tupleToActionButtonEntry(server,
                new Tuple4<>(methodAdd, GenerateGlobals.ProtocolType.route, "新增", Lists.newArrayList()
                )));


        //搜索栏
        List<GridLayoutEntry> searchGrids = Lists.newArrayList(
                GridLayoutUtils.newGridLayout(3, Lists.newArrayList(
                        InputColumnEntry.builder().label("算法名称").prop("name").build()
                ))
        );


        //数据栏
        List<TableColumnEntry> tableColumns = Lists.newArrayList(
                TableColumnEntry.builder().label("ID").prop("id").columnType(GenerateGlobals.TableColumnType.text).width("50").build(),
                TableColumnEntry.builder().label("创建时间").prop("createDate").columnType(GenerateGlobals.TableColumnType.text).width("").build(),
                TableColumnEntry.builder().label("算法名称").prop("name").columnType(GenerateGlobals.TableColumnType.text).width("").build(),
                TableColumnEntry.builder().label("算法类型").prop("typeName").columnType(GenerateGlobals.TableColumnType.text).width("").build(),
                TableColumnEntry.builder().label("算法访问地址").prop("serverUrl").columnType(GenerateGlobals.TableColumnType.text).width("").build()
        );

        //数据操作栏
        List<ButtonEntry> tableOperationActionButtons = Lists.newArrayList();
        tableOperationActionButtons.add(CmsUtils.tupleToOpartionButtonEntry(server,
                new Tuple4<>(methodUpdate, GenerateGlobals.ProtocolType.route, "编辑", Lists.newArrayList())
        ));
        tableOperationActionButtons.add(
                ButtonEntry.builder()
                        .url(CmsUtils.methodToRequest(methodDelete))
                        .label("删除")
                        .icon("el-icon-delete")
                        .btnStyle(GenerateGlobals.ButtonStyle.danger)
                        .condition("'#{type}' == 'NORMAL'")
                        .role(ControllerUtil.getRoleByMethod(methodDelete))
                        .build()
        );

        /*---------------------------- 基础架构-一般情况下不用动 ------------------------------- */
        CmsPage page = StandardUtils.standardListPage(methodPage, actionButtons,
                searchGrids, tableOperationActionButtons, tableColumns, true, false);

        String token = PostSystem.getToken(server, username, password);
        PostSystem.postVersion(server, token, methodPage.getName(), JSON.toJSONString(page));
    }

    @Test
    public void formUpdate(){
        Method methodUpdate = CmsUtils.findMethodByName(cla, updateMethod);
        Method methodDetails = CmsUtils.findMethodByName(cla, detailsMethod);

        List<LayoutEntry> formColumns = Lists.newArrayList();

        formColumns.add(HeaderLayoutEntry.builder().label("基础信息").build());
        formColumns.add(
                GridLayoutUtils.newGridLayout(2, Lists.newArrayList(
                        InputColumnEntry.builder().label("算法名称").prop("name").required(true).build(),
                        InputColumnEntry.builder().label("算法访问地址").prop("serverUrl").required(true).build()
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

        formColumns.add(HeaderLayoutEntry.builder().label("基础信息").build());
        formColumns.add(
                GridLayoutUtils.newGridLayout(3, Lists.newArrayList(
                        SelectColumnEntry.builder().label("算法类型").prop("type").required(true)
                                .options(Lists.newArrayList(
                                        new Option("基准算法", "STANDARD"),
                                        new Option("普通算法", "NORMAL")
                                ))
                                .build(),
                        InputColumnEntry.builder().label("算法名称").prop("name").required(true).build(),
                        InputColumnEntry.builder().label("算法访问地址").prop("serverUrl").required(true).build()
                ))
        );

        CmsPage page = StandardUtils.standardFormPage(methodUpdate, formColumns, Lists.newArrayList());
        String token = PostSystem.getToken(server, username, password);
        PostSystem.postVersion(server, token, methodUpdate.getName(), JSON.toJSONString(page));
    }

}
