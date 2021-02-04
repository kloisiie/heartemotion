package cn.bluetech.gragas.web.api;

import cn.bluetech.gragas.aop.annotation.APILog;
import cn.bluetech.gragas.entity.hr.Arithmetic;
import cn.bluetech.gragas.entity.hr.ClientDebugFile;
import cn.bluetech.gragas.entity.hr.PlatformDebugFile;
import cn.bluetech.gragas.json.admin.hr.*;
import cn.bluetech.gragas.json.api.hr.DebugExecuteArithmeticEntryResult;
import cn.bluetech.gragas.json.api.hr.DebugExecuteArithmeticResult;
import cn.bluetech.gragas.json.api.hr.DebugExecuteParam;
import cn.bluetech.gragas.json.api.hr.DebugExecuteResult;
import cn.bluetech.gragas.pojo.hr.*;
import cn.bluetech.gragas.service.hr.ArithmeticDebugService;
import cn.bluetech.gragas.service.hr.ArithmeticService;
import cn.bluetech.gragas.service.hr.ClientDebugFileService;
import cn.bluetech.gragas.service.hr.PlatformDebugFileService;
import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.brframework.common.utils.DateTimeUtils;
import com.brframework.commonsecurity.core.SecurityContextHolder;
import com.brframework.commonweb.json.JSONResult;
import com.brframework.commonweb.json.Page;
import com.brframework.commonweb.json.PageParam;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static com.brframework.commondb.core.ControllerPageHandle.jpaPageHandleToPage;

/**
 * @author xu
 * @date 2020/12/16 21:30
 */
@RestController
@Api(tags = "算法调试模块")
@RequestMapping("/api")
@Slf4j
public class ApiArithmeticDebugController {

    /** 用户调试文件Service */
    @Autowired
    public ClientDebugFileService clientDebugFileService;

    /** 平台调试文件Service */
    @Autowired
    public PlatformDebugFileService platformDebugFileService;

    /** 算法Service */
    @Autowired
    public ArithmeticService arithmeticService;

    @Autowired
    public ArithmeticDebugService arithmeticDebugService;



    @ApiOperation(value = "用户调试文件列表", notes = "用户调试文件列表")
    @GetMapping("/access/v1/hr/client-debug-file/list")
    @APILog
    public JSONResult<Page<ClientDebugFileQueryResult>> clientDebugFilePageV1(PageParam pageParam,
                                                                              ClientDebugFilePageQueryParam queryParam){

        ClientDebugFilePageQueryParamDTO dtoParam = new ClientDebugFilePageQueryParamDTO();
        dtoParam.setClientId(SecurityContextHolder.getUserDetails().getId());

        return JSONResult.ok(jpaPageHandleToPage(
                clientDebugFileService.findClientDebugFilePage(pageParam, dtoParam), pageParam,
                this::clientDebugFileToResult
        ));

    }

    @ApiOperation(value = "平台调试文件列表", notes = "平台调试文件列表")
    @GetMapping("/access/v1/hr/platform-debug-file/list")
    @APILog
    public JSONResult<Page<PlatformDebugFileQueryResult>> platformDebugFilePageV1(PageParam pageParam,
                                                                                  PlatformDebugFilePageQueryParam queryParam){

        PlatformDebugFilePageQueryParamDTO dtoParam = new PlatformDebugFilePageQueryParamDTO();
        dtoParam.setFileName(queryParam.getFileName());

        return JSONResult.ok(jpaPageHandleToPage(
                platformDebugFileService.findPlatformDebugFilePage(pageParam, dtoParam), pageParam,
                this::platformDebugFileToResult
        ));

    }


    @ApiOperation(value = "算法列表", notes = "算法列表")
    @GetMapping("/access/v1/hr/arithmetic/list")
    @APILog
    public JSONResult<List<ArithmeticQueryResult>> arithmeticPageV1(){

        return JSONResult.ok(
                arithmeticService.listArithmetic()
                        .stream()
                        .map(this::arithmeticToResult)
                        .collect(Collectors.toList())
        );
    }



    @ApiOperation(value = "添加用户调试文件(本地文件)", notes = "添加用户调试文件(本地文件)")
    @PostMapping("/access/v1/hr/client-debug-file/local/add")
    @APILog
    public JSONResult clientDebugFileLocalAddV1(@RequestBody @Valid ClientDebugFileInsertParam insertParam){

        ClientDebugFileInsertParamDTO dtoParam = new ClientDebugFileInsertParamDTO();
        dtoParam.setFileName(insertParam.getFileName());
        dtoParam.setContent(insertParam.getContent());
        dtoParam.setClientId(SecurityContextHolder.getUserDetails().getId());

        clientDebugFileService.insertClientDebugFile(dtoParam);
        return JSONResult.ok();

    }

    @ApiOperation(value = "添加用户调试文件(云端文件)", notes = "添加用户调试文件(云端文件)")
    @PostMapping("/access/v1/hr/client-debug-file/platform/add")
    @APILog
    public JSONResult clientDebugFilePlatformAddV1(@RequestParam String platformDebugFileIds){

        for (String s : platformDebugFileIds.split(",")) {
            PlatformDebugFile platformDebugFile = platformDebugFileService.findPlatformDebugFileById(Long.parseLong(s));
            ClientDebugFileInsertParamDTO dtoParam = new ClientDebugFileInsertParamDTO();
            dtoParam.setFileName(platformDebugFile.getFileName());
            dtoParam.setContent(platformDebugFile.getContent());
            dtoParam.setClientId(SecurityContextHolder.getUserDetails().getId());

            clientDebugFileService.insertClientDebugFile(dtoParam);
        }

        return JSONResult.ok();
    }


    @ApiOperation(value = "删除用户调试文件", notes = "删除用户调试文件")
    @DeleteMapping("/access/v1/hr/client-debug-file/del")
    @APILog
    public JSONResult clientDebugFileDeleteV1(@RequestParam String clientDebugFileIds){

        for (String s : clientDebugFileIds.split(",")) {
            clientDebugFileService.removeClientDebugFileById(Long.parseLong(s));
        }


        return JSONResult.ok();
    }


    @ApiOperation(value = "执行算法", notes = "执行算法")
    @PostMapping("/access/v1/hr/debug/execute")
    @APILog
    public JSONResult<DebugExecuteResult> debugExecute(@Valid @RequestBody DebugExecuteParam param){

        JSONArray array = new JSONArray();
        for (Long clientDebugFileId : param.getClientDebugFileIds()) {
            ClientDebugFile clientDebugFile = clientDebugFileService.findClientDebugFileById(clientDebugFileId);
            array.addAll(JSON.parseArray(clientDebugFile.getContent()));
        }

        ArithmeticDebugInsertParamDTO insertParam = new ArithmeticDebugInsertParamDTO();
        insertParam.setStandardResult(array.toJSONString());
        insertParam.setArithmeticId(param.getArithmeticId());
        insertParam.setClientId(SecurityContextHolder.getUserDetails().getId());

        DebugExecuteResult result = new DebugExecuteResult();
        result.setTaskId(arithmeticDebugService.insertArithmeticDebug(insertParam));

        return JSONResult.ok(result);
    }

    @ApiOperation(value = "算法执行结果", notes = "算法执行结果")
    @GetMapping("/access/v1/hr/debug/execute/result/{taskId}")
    @APILog
    public JSONResult<DebugExecuteArithmeticResult> debugExecuteResult(@PathVariable String taskId){
        return JSONResult.ok(arithmeticDebugService.debugResult(taskId));
    }


    @ApiOperation(value = "算法执行结果导出", notes = "算法执行结果导出")
    @GetMapping("/v1/hr/debug/execute/export/{taskId}")
    @APILog
    public void debugExecuteExport1(@PathVariable String taskId, HttpServletResponse response) throws IOException {
        debugExecuteExport(taskId, response);
    }

    @ApiOperation(value = "算法执行结果导出", notes = "算法执行结果导出")
    @GetMapping("/access/v1/hr/debug/execute/export/{taskId}")
    @APILog
    public void debugExecuteExport(@PathVariable String taskId, HttpServletResponse response) throws IOException {
        List<List<String>> rows = Lists.newArrayList();

        DebugExecuteArithmeticResult result = arithmeticDebugService.debugResult(taskId);
        for (DebugExecuteArithmeticEntryResult enter : result.getEnters()) {
            rows.add(Lists.newArrayList(DateTimeUtils.dateTimeToString(enter.getHrTime()),
                    enter.getHrValue() + "", enter.getStandard().ordinal() + "", enter.getNormal().ordinal() + ""));
        }

        // 通过工具类创建writer，默认创建xls格式
        ExcelWriter writer = ExcelUtil.getWriter();
        // 一次性写出内容，使用默认样式，强制输出标题
        writer.merge(3, "算法执行结果");
        writer.writeRow(Lists.newArrayList(Lists.newArrayList("time", "hr", "standard", "normal")));
        writer.write(rows, true);

        //out为OutputStream，需要写出到的目标流

        //response为HttpServletResponse对象
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        //test.xls是弹出下载对话框的文件名，不能为中文，中文请自行编码
        response.setHeader("Content-Disposition","attachment;filename=export.xls");
        ServletOutputStream out= response.getOutputStream();

        writer.flush(out, true);
        // 关闭writer，释放内存
        writer.close();
        //此处记得关闭输出Servlet流
        IoUtil.close(out);
    }



    /**
     * 用户调试文件Result转换
     * @param clientDebugFile    用户调试文件
     */
    private ClientDebugFileQueryResult clientDebugFileToResult(ClientDebugFile clientDebugFile){

        ClientDebugFileQueryResult result = new ClientDebugFileQueryResult();
        result.setId(clientDebugFile.getId());
        result.setCreateDate(clientDebugFile.getCreateDate());
        result.setFileName(clientDebugFile.getFileName());
        result.setDetails(clientDebugFile.getDetails());
        result.setStartTime(clientDebugFile.getStartTime());
        result.setEndTime(clientDebugFile.getEndTime());

        return result;

    }


    /**
     * 算法Result转换
     * @param arithmetic    算法
     */
    private ArithmeticQueryResult arithmeticToResult(Arithmetic arithmetic){

        ArithmeticQueryResult result = new ArithmeticQueryResult();
        result.setId(arithmetic.getId());
        result.setCreateDate(arithmetic.getCreateDate());
        result.setName(arithmetic.getName());
        result.setServerUrl(arithmetic.getServerUrl());
        result.setType(arithmetic.getType());

        return result;

    }


    /**
     * 平台调试文件Result转换
     * @param platformDebugFile    平台调试文件
     */
    private PlatformDebugFileQueryResult platformDebugFileToResult(PlatformDebugFile platformDebugFile){

        PlatformDebugFileQueryResult result = new PlatformDebugFileQueryResult();
        result.setId(platformDebugFile.getId());
        result.setCreateDate(platformDebugFile.getCreateDate());
        result.setFileName(platformDebugFile.getFileName());
        result.setDetails(platformDebugFile.getDetails());
        result.setStartTime(platformDebugFile.getStartTime());
        result.setEndTime(platformDebugFile.getEndTime());

        return result;

    }







}
