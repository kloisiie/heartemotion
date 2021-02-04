package cn.bluetech.gragas.web.admin;

import java.io.IOException;
import java.lang.*;
import java.util.List;
import java.util.stream.Collectors;

import cn.bluetech.gragas.globals.HeartRateConstant;
import cn.bluetech.gragas.json.admin.hr.*;
import cn.bluetech.gragas.json.api.hr.HeartRateExportResult;
import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSON;
import com.brframework.common.utils.DateTimeUtils;
import com.brframework.commonweb.exception.HandleException;
import com.brframework.commonweb.json.PageParam;
import cn.bluetech.gragas.entity.hr.*;
import cn.bluetech.gragas.pojo.hr.*;
import cn.bluetech.gragas.service.hr.*;
import com.brframework.commonweb.utils.ServletUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.extern.slf4j.Slf4j;
import com.brframework.commonweb.json.JSONResult;
import com.brframework.commonweb.json.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import static com.brframework.commondb.core.ControllerPageHandle.jpaPageHandleToPage;


/**
 * 心率模块
 * @author xu
 * @date 2020-12-16 17:11:27
 */ 
@RestController
@Api(tags = "心率模块")
@RequestMapping("/admin/access")
@Slf4j
public class AdminHeartRateController{

    /** 心率Service */
    @Autowired
    public HeartRateService heartRateService;


    @Autowired
    public DeviceService deviceService;

    
    @ApiOperation(value = "标注列表", notes = "心率列表")
    @GetMapping("/v1/hr/heart-rate/list")
    @PreAuthorize("hasRole('hr-heart-rate-query')")
    public JSONResult<Page<HeartRateQueryResult>> heartRatePageV1(PageParam pageParam,
                                                                  HeartRatePageQueryParam queryParam){

        HeartRatePageQueryParamDTO dtoParam = new HeartRatePageQueryParamDTO();
        if(queryParam.getHrTime() != null && queryParam.getHrTime().size() > 0){
            dtoParam.setHrTimeStart(queryParam.getHrTime().get(0));
            dtoParam.setHrTimeEnd(queryParam.getHrTime().get(1));
        }

        dtoParam.setDeviceId(queryParam.getDeviceId());
        dtoParam.setWearer(queryParam.getWearer());
        if(queryParam.getLabelStatus() != null && queryParam.getLabelStatus().size() > 0){
            dtoParam.setLabelStatus(HeartRateConstant.LabelStatus.valueOf(queryParam.getLabelStatus().get(0)));
        }

        if(queryParam.getLabelType() != null && queryParam.getLabelType().size() > 0){
            dtoParam.setLabelType(HeartRateConstant.LabelType.valueOf(queryParam.getLabelType().get(0)));
        }

        return JSONResult.ok(jpaPageHandleToPage(
                heartRateService.findHeartRatePage(pageParam, dtoParam), pageParam,
                this::heartRateToResult
        ));
    }

    @ApiOperation(value = "批量删除心率", notes = "批量删除心率")
    @DeleteMapping("/v1/hr/heart-rate/batch-del")
    @PreAuthorize("hasRole('hr-heart-rate-del')")
    public JSONResult heartRateBatchDeleteV1(@RequestParam("ids") List<Long> ids){

        for (Long id : ids) {
            heartRateService.removeHeartRateById(id);
        }

        return JSONResult.ok();

    }

    @ApiOperation(value = "导出", notes = "导出")
    @GetMapping("/v1/hr/heart-rate/export")
    @PreAuthorize("hasRole('hr-heart-export')")
    public void heartRateExportV1(HeartRatePageQueryParam queryParam){
        HeartRatePageQueryParamDTO dtoParam = new HeartRatePageQueryParamDTO();
        if(queryParam.getHrTime() != null && queryParam.getHrTime().size() > 0){
            dtoParam.setHrTimeStart(queryParam.getHrTime().get(0));
            dtoParam.setHrTimeEnd(queryParam.getHrTime().get(1));
        }

        dtoParam.setDeviceId(queryParam.getDeviceId());
        if(queryParam.getLabelStatus() != null && queryParam.getLabelStatus().size() > 0){
            dtoParam.setLabelStatus(HeartRateConstant.LabelStatus.valueOf(queryParam.getLabelStatus().get(0)));
        }

        if(queryParam.getLabelType() != null && queryParam.getLabelType().size() > 0){
            dtoParam.setLabelType(HeartRateConstant.LabelType.valueOf(queryParam.getLabelType().get(0)));
        }

        PageParam pageParam = new PageParam();
        pageParam.setPageIndex(1);
        pageParam.setPageSize(Integer.MAX_VALUE);

        List<HeartRateExportResult> collect = heartRateService.findHeartRatePage(pageParam, dtoParam).getContent()
                .stream()
                .map(hr -> {
                    HeartRateExportResult result = new HeartRateExportResult();
                    result.setHrTime(DateTimeUtils.toEpochSecond(hr.getHrTime()));
                    result.setHrValue(hr.getHrValue());
                    result.setLabelStatus(hr.getLabelStatus().ordinal());
                    result.setMeans(hr.getMeans());
                    result.setLabelType(hr.getLabelType().ordinal());
                    return result;
                })
                .collect(Collectors.toList());

        HttpServletResponse httpServletResponse = ServletUtils.response();
        //开始到处excel文件
        //1.设置文件ContentType类型，这样设置，会自动判断下载文件类型
        httpServletResponse.setContentType("multipart/form-data");
        //2.设置文件头：最后一个参数是设置下载文件名
        httpServletResponse.setHeader("Content-Disposition", "attachment;fileName=export-" +
                RandomUtil.randomString(5) +".json");
        try {
            ServletOutputStream outputStream = httpServletResponse.getOutputStream();
            outputStream.write(JSON.toJSONBytes(collect));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 心率Result转换 
     * @param heartRate    心率 
     */
    private HeartRateQueryResult heartRateToResult(HeartRate heartRate){

        HeartRateQueryResult result = new HeartRateQueryResult();
        result.setId(heartRate.getId());
        result.setHrTime(heartRate.getHrTime());
        result.setDeviceId(heartRate.getDeviceId());
        result.setWearer(heartRate.getWearer());
        result.setHrValue(heartRate.getHrValue());
        result.setLabelStatus(heartRate.getLabelStatus());
        result.setLabelStatusName(heartRate.getLabelStatus() != null ? heartRate.getLabelStatus().describe() : null);
        result.setMeans(heartRate.getMeans());
        result.setLabelType(heartRate.getLabelType());
        result.setLabelTypeName(heartRate.getLabelType() != null ? heartRate.getLabelType().describe() : null);

        return result;
            
    }




}