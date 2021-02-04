package cn.bluetech.gragas.cms;

import cn.bluetech.gragas.web.admin.AdminPlatformDebugFileController;
import cn.bluetech.gragas.web.admin.AdminStudentController;
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
 * @date 2020/12/23 10:37
 */
@RunWith(value = JUnit4.class)
public class PlatformDebugFileGenerate {

    private final static String server = "https://dev.api.beiru168.com/gragas";
    private final static String username = "admin";
    private final static String password = "123456";
    private final static Class cla = AdminPlatformDebugFileController.class;
    private final static String queryPageMethod = "platformDebugFilePageV1";
    private final static String detailsMethod = "platformDebugFileDetailsV1";
    private final static String addMethod = "platformDebugFileAddV1";
    private final static String updateMethod = "platformDebugFileUpdateV1";
    private final static String deleteMethod = "platformDebugFileDeleteV1";
    private final static String batchDeleteMethod = "platformDebugFileBatchDeleteV1";

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
        List<GridLayoutEntry> searchGrids = Lists.newArrayList(
                GridLayoutUtils.newGridLayout(3, Lists.newArrayList(
                        InputColumnEntry.builder().label("文件名").prop("fileName").build()
                ))
        );


        //数据栏
        List<TableColumnEntry> tableColumns = Lists.newArrayList(
                TableColumnEntry.builder().label("ID").prop("id").columnType(GenerateGlobals.TableColumnType.text).width("50").build(),
                TableColumnEntry.builder().label("创建时间").prop("createDate").columnType(GenerateGlobals.TableColumnType.text).width("").build(),
                TableColumnEntry.builder().label("文件名").prop("fileName").columnType(GenerateGlobals.TableColumnType.text).width("").build(),
                TableColumnEntry.builder().label("描述").prop("details").columnType(GenerateGlobals.TableColumnType.text).width("").build(),
                TableColumnEntry.builder().label("开始时间").prop("startTime").columnType(GenerateGlobals.TableColumnType.text).width("").build(),
                TableColumnEntry.builder().label("结束时间").prop("endTime").columnType(GenerateGlobals.TableColumnType.text).width("").build()
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

        formColumns.add(HeaderLayoutEntry.builder().label("基础资料").build());
        formColumns.add(
                GridLayoutUtils.newGridLayout(2, Lists.newArrayList(
                        InputColumnEntry.builder().label("文件名").prop("fileName").required(true).build(),
                        InputColumnEntry.builder().label("描述").prop("details").required(true).build()
                ))
        );

        formColumns.add(
                GridLayoutUtils.newGridLayout(1, Lists.newArrayList(
                        FileUploadColumnEntry.builder().label("文件内容").prop("content")
                                .limit(1)
                                .multiple(false)
                                .required(true).build()
                ))
        );

        return formColumns;
    }
}
