package cn.bluetech.gragas.cms;

import cn.bluetech.gragas.web.admin.AdminStudentController;
import com.alibaba.fastjson.JSON;
import com.brframework.cms2.generate.*;
import com.brframework.cms2.generate.column.*;
import com.brframework.cms2.generate.layout.*;
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
 * @date 2020/12/9 11:47
 */
@RunWith(value = JUnit4.class)
public class StudentCmsGenerate {

    private final static String server = "https://dev.api.beiru168.com/gragas";
    private final static String username = "admin";
    private final static String password = "123456";
    private final static Class cla = AdminStudentController.class;
    private final static String queryPageMethod = "studentPageV1";
    private final static String detailsMethod = "studentDetailsV1";
    private final static String addMethod = "studentAddV1";
    private final static String updateMethod = "studentUpdateV1";
    private final static String deleteMethod = "studentDeleteV1";
    private final static String batchDeleteMethod = "studentBatchDeleteV1";

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
        List<GridLayoutEntry> searchGrids = Lists.newArrayList(
                GridLayoutUtils.newGridLayout(3, Lists.newArrayList(
                        InputColumnEntry.builder().label("名称").prop("name").build(),
                        InputColumnEntry.builder().label("年龄区间").prop("ageBetween").build(),
                        SelectColumnEntry.builder().label("爱好").prop("hobby").multiple(true).options(Lists.newArrayList(
                                new Option("游泳", "游泳"),
                                new Option("爬山", "爬山"),
                                new Option("跳绳", "跳绳"),
                                new Option("骑行", "骑行"),
                                new Option("作死", "作死"),
                                new Option("开摩托", "开摩托")
                        )).required(true).build(),
                        DatePickerDaterangeColumnEntry.builder().label("生日区间").prop("birthdayBetween").build(),
                        CascaderColumnEntry.builder().label("省市区").prop("provinceCityDistrict")
                                .level(3)
                                .dynamicUrl(CmsUtils.dictToRequest("test-area-query"))
                                .build(),
                        NumberColumnEntry.builder().label("自我评分").prop("rating").build()
                ))
        );


        //数据栏
        List<TableColumnEntry> tableColumns = Lists.newArrayList(
                TableColumnEntry.builder().label("id").prop("id").columnType(GenerateGlobals.TableColumnType.text).width("50").build(),
                TableColumnEntry.builder().label("创建时间").prop("createDate").columnType(GenerateGlobals.TableColumnType.text).width("").build(),
                TableColumnEntry.builder().label("名称").prop("name").columnType(GenerateGlobals.TableColumnType.text).width("").build(),
                TableColumnEntry.builder().label("年龄").prop("age").columnType(GenerateGlobals.TableColumnType.text).width("50").build(),
                TableColumnEntry.builder().label("爱好").prop("hobby").columnType(GenerateGlobals.TableColumnType.text).width("").build(),
                TableColumnEntry.builder().label("生日").prop("birthday").columnType(GenerateGlobals.TableColumnType.text).width("").build(),
                TableColumnEntry.builder().label("省市区").prop("provinceCityDistrict").columnType(GenerateGlobals.TableColumnType.text).width("").build(),
                TableColumnEntry.builder().label("头像").prop("headImage").columnType(GenerateGlobals.TableColumnType.image).width("").build(),
                TableColumnEntry.builder().label("自我评分").prop("rating").columnType(GenerateGlobals.TableColumnType.text).width("50").build()
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
                GridLayoutUtils.newGridLayout(3, Lists.newArrayList(
                        InputColumnEntry.builder().label("名称").prop("name").required(true).minLength(1).maxLength(50).build(),
                        NumberColumnEntry.builder().label("年龄").prop("age").required(true).min(1).max(100).build(),
                        DatePickerDateColumnEntry.builder().label("生日").prop("birthday").required(true).build(),
                        CascaderColumnEntry.builder().label("省市区").prop("provinceCityDistrict").required(true)
                                .level(3)
                                .dynamicUrl(CmsUtils.dictToRequest("test-area"))
                                .build(),
                        RateColumnEntry.builder().label("自我评分").prop("rating").required(true).build(),
                        SelectColumnEntry.builder().label("爱好").prop("hobby").multiple(true).options(Lists.newArrayList(
                                new Option("游泳", "游泳"),
                                new Option("爬山", "爬山"),
                                new Option("跳绳", "跳绳"),
                                new Option("骑行", "骑行"),
                                new Option("作死", "作死"),
                                new Option("开摩托", "开摩托")
                        )).required(true).build()
                ))
        );

        formColumns.add(HeaderLayoutEntry.builder().label("影像资料").build());

        formColumns.add(
                GridLayoutUtils.newGridLayout(2, Lists.newArrayList(
                        ImageUploadColumnEntry.builder().label("头像").prop("headImage").limit(1).sizeLimit(5).required(true).build(),
                        ImageUploadColumnEntry.builder().label("生活照片").prop("images").multiple(true).limit(3).sizeLimit(5).placeholder("个人生活照|限3张").required(true).build()
                ))
        );

        formColumns.add(HeaderLayoutEntry.builder().label("附加信息").build());
        formColumns.add(
                GridLayoutUtils.newGridLayout(1, Lists.newArrayList(
                        FileUploadColumnEntry.builder().label("附件").prop("accessory").multiple(true).limit(20).sizeLimit(30).placeholder("个人附件信息").required(true).build()
                ))
        );
        formColumns.add(
                GridLayoutUtils.newGridLayout(1, Lists.newArrayList(
                        TextareaColumnEntry.builder().label("个性签名").prop("signature").required(true).build()
                ))
        );
        formColumns.add(
                GridLayoutUtils.newGridLayout(1, Lists.newArrayList(
                        RichTextColumnEntry.builder().label("自我介绍").prop("introduction").required(true).build()

                ))
        );
        return formColumns;
    }
}
