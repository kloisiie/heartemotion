package cn.bluetech.gragas.web.api;

import cn.bluetech.gragas.aop.annotation.APILog;
import cn.bluetech.gragas.entity.hr.HeartRate;
import cn.bluetech.gragas.globals.Constant;
import cn.bluetech.gragas.json.api.hr.LabelInsertParam;
import cn.bluetech.gragas.json.api.hr.*;
import cn.bluetech.gragas.pojo.hr.HeartRateInsertParamDTO;
import cn.bluetech.gragas.pojo.hr.HeartRateListQueryParamDTO;
import cn.bluetech.gragas.service.hr.DeviceService;
import cn.bluetech.gragas.service.hr.HeartRateService;
import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSON;
import com.brframework.common.utils.DateTimeUtils;
import com.brframework.commonsecurity.core.SecurityContextHolder;
import com.brframework.commonweb.json.JSONResult;
import com.brframework.commonweb.utils.ServletUtils;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author xu
 * @date 2020/12/16 18:11
 */
@RestController
@Api(tags = "心率模块")
@RequestMapping("/api")
@Slf4j
public class ApiHeartRateController {


    /** 心率Service */
    @Autowired
    public HeartRateService heartRateService;

    @Autowired
    public DeviceService deviceService;


    @ApiOperation(value = "心率列表", notes = "心率列表")
    @GetMapping("/access/v1/hr/heart-rate/{deviceId}/list")
    @APILog
    public JSONResult<List<HeartRateQueryResult>> heartRatePageV1(@PathVariable String deviceId,
                                                                  HeartRatePageQueryParam queryParam){

        HeartRateListQueryParamDTO dtoParam = new HeartRateListQueryParamDTO();
        if(queryParam.getHrTimeStart() != null && queryParam.getHrTimeEnd() != null){
            dtoParam.setHrTimeStart(queryParam.getHrTimeStart());
            dtoParam.setHrTimeEnd(queryParam.getHrTimeEnd());
        } else {
            dtoParam.setHrTimeStart(LocalDateTime.now().minusHours(12));
            dtoParam.setHrTimeEnd(LocalDateTime.now());
        }

        List<HeartRate> heartRates = heartRateService.listHeartRate(deviceId, dtoParam);
        List<HeartRateQueryResult> results = Lists.newArrayList();
        for (List<HeartRate> rates : timeSplit(heartRates)) {
            results.addAll(statusSplit(rates));
        }

        return JSONResult.ok(
                results
        );
    }

    private List<List<HeartRate>> timeSplit(List<HeartRate> heartRates){
        List<List<HeartRate>> lists = Lists.newArrayList();
        //进行分割
        boolean split = true;
        HeartRate previous = null;
        List<HeartRate> hr = null;
        for (HeartRate heartRate : heartRates) {
            if(split){
                hr = Lists.newArrayList();
                lists.add(hr);
                split = false;
            }

            if(previous != null){
                long second = DateTimeUtils.toEpochSecond(heartRate.getHrTime()) -
                        DateTimeUtils.toEpochSecond(previous.getHrTime());
                if(second > 30){
                    split = true;
                }
            }

            hr.add(heartRate);

            previous = heartRate;
        }
        return lists;
    }

    private List<HeartRateQueryResult> statusSplit(List<HeartRate> heartRates){
        List<HeartRateQueryResult> lists = Lists.newArrayList();
        //进行分割
        boolean split = true;
        HeartRate previous = null;
        List<HeartRateQueryEntryResult> hr = null;
        for (HeartRate heartRate : heartRates) {
            if(split){
                hr = Lists.newArrayList();
                HeartRateQueryResult result = new HeartRateQueryResult();
                result.setLabelStatus(heartRate.getLabelStatus());
                result.setEnters(hr);
                result.setLabelType(heartRate.getLabelType());
                lists.add(result);
                split = false;
            }

            if(previous != null && (!heartRate.getLabelStatus().equals(previous.getLabelStatus()) ||
                    !heartRate.getLabelType().equals(previous.getLabelType()))){
                split = true;
            }

            HeartRateQueryEntryResult entry = new HeartRateQueryEntryResult();
            entry.setHrTime(heartRate.getHrTime());
            entry.setHrValue(heartRate.getHrValue());

            hr.add(entry);

            previous = heartRate;
        }
        return lists;
    }


    @ApiOperation(value = "心率列表(标注)", notes = "心率列表(标注)")
    @GetMapping("/access/v1/hr/heart-rate/{deviceId}/label/list")
    public JSONResult<List<HeartRateQueryEntryResult>> heartRateLabelPageV1(@PathVariable String deviceId,
                                                                            HeartRatePageQueryParam queryParam){
        HeartRateListQueryParamDTO dtoParam = new HeartRateListQueryParamDTO();
        if(queryParam.getHrTimeStart() != null && queryParam.getHrTimeEnd() != null){
            dtoParam.setHrTimeStart(queryParam.getHrTimeStart());
            dtoParam.setHrTimeEnd(queryParam.getHrTimeEnd());
        } else {
            dtoParam.setHrTimeStart(LocalDateTime.now().minusHours(12));
            dtoParam.setHrTimeEnd(LocalDateTime.now());
        }

        return JSONResult.ok(
                heartRateService.listHeartRate(deviceId, dtoParam).stream()
                        .map(heartRate -> {
                            HeartRateQueryEntryResult result = new HeartRateQueryEntryResult();
                            result.setHrTime(heartRate.getHrTime());
                            result.setHrValue(heartRate.getHrValue());
                            return result;
                        })
                        .collect(Collectors.toList())
        );
    }


    @ApiOperation(value = "心率文件导出", notes = "心率文件导出")
    @GetMapping(value = "/access/v1/hr/heart-rate/{deviceId}/export")
    public void heartRateExportV1(@PathVariable String deviceId,@Valid HeartRateExportParam queryParam){
        HeartRateListQueryParamDTO dtoParam = new HeartRateListQueryParamDTO();
        dtoParam.setHrTimeStart(queryParam.getHrTimeStart());
        dtoParam.setHrTimeEnd(queryParam.getHrTimeEnd());

        List<HeartRateExportResult> collect = heartRateService.listHeartRate(deviceId, dtoParam)
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


    @ApiOperation(value = "心率数据提交", notes = "心率数据提交")
    @PostMapping("/access/v1/hr/heart-rate/{deviceId}/post")
    public JSONResult heartRateAddV1(@PathVariable String deviceId,
                                     @RequestBody @Valid HeartRateInsertParam insertParam){
        HeartRateInsertParamDTO dtoParam = new HeartRateInsertParamDTO();
        dtoParam.setHrTime(insertParam.getHrTime());
        dtoParam.setDeviceId(deviceId);
        dtoParam.setHrValue(insertParam.getHrValue());
        dtoParam.setWearer(deviceService.findWearerByDeviceId(SecurityContextHolder.getUserDetails().getId(),
                deviceId));

        heartRateService.insertHeartRate(dtoParam);
        return JSONResult.ok();

    }


    @ApiOperation(value = "添加标注", notes = "添加标注")
    @PostMapping("/access/v1/hr/label/add")
    public JSONResult<LabelAddResult> labelAddV1(@RequestBody @Valid LabelInsertParam insertParam){
        LabelAddResult result = new LabelAddResult();
        if(!insertParam.isOverride()){
            //不覆盖的情况下，去找是否存在已经有标注的数据
            boolean checkStatus = heartRateService.checkStatus(insertParam.getDeviceId(),
                    insertParam.getLabelTimeStart(), insertParam.getLabelTimeEnd());
            if(checkStatus){
                result.setExist(Constant.Is.YES);
                return JSONResult.ok(result);
            }
        }

        heartRateService.updateLabelStatus(insertParam.getDeviceId(), insertParam.getLabelTimeStart(),
                insertParam.getLabelTimeEnd(), insertParam.getLabelStatus(), insertParam.getMeans());

        return JSONResult.ok(result);

    }


    /**
     * 心率DetailResult转换
     * @param heartRate    心率
     */
    private HeartRateDetailResult heartRateToDetailResult(HeartRate heartRate){

        HeartRateDetailResult result = new HeartRateDetailResult();
        result.setId(heartRate.getId());
        result.setCreateDate(heartRate.getCreateDate());
        result.setHrTime(heartRate.getHrTime());
        result.setDeviceId(heartRate.getDeviceId());
        result.setHrValue(heartRate.getHrValue());

        return result;

    }

}
