package cn.bluetech.gragas.web.api;

import cn.bluetech.gragas.aop.annotation.APILog;
import cn.bluetech.gragas.entity.hr.Device;
import cn.bluetech.gragas.entity.hr.HeartRate;
import cn.bluetech.gragas.json.api.hr.DeviceDetailResult;
import cn.bluetech.gragas.json.api.hr.DeviceInsertParam;
import cn.bluetech.gragas.json.api.hr.DeviceQueryResult;
import cn.bluetech.gragas.json.api.hr.DeviceUpdateParam;
import cn.bluetech.gragas.pojo.hr.DeviceInsertParamDTO;
import cn.bluetech.gragas.pojo.hr.DevicePageQueryParamDTO;
import cn.bluetech.gragas.pojo.hr.DeviceUpdateParamDTO;
import cn.bluetech.gragas.service.hr.DeviceService;
import cn.bluetech.gragas.service.hr.HeartRateService;
import cn.bluetech.gragas.service.hr.PoliceService;
import com.brframework.commondb.core.ControllerPageHandle;
import com.brframework.commonsecurity.core.SecurityContextHolder;
import com.brframework.commonweb.json.JSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author xu
 * @date 2020/12/16 17:56
 */
@RestController
@Api(tags = "设备模块")
@RequestMapping("/api")
@Slf4j
public class ApiDeviceController {

    /** 设备Service */
    @Autowired
    public DeviceService deviceService;

    @Autowired
    public HeartRateService heartRateService;

    @Autowired
    public PoliceService policeService;

    @ApiOperation(value = "所有绑定设备", notes = "所有绑定设备")
    @GetMapping("/access/v1/hr/device/list")
    @APILog
    public JSONResult<List<DeviceQueryResult>> devicePageV1(){
        String clientId = SecurityContextHolder.getUserDetails().getId();

        DevicePageQueryParamDTO param = new DevicePageQueryParamDTO();
        param.setClientId(clientId);

        return JSONResult.ok(
                deviceService.listDevice(param).stream().map(this::deviceToResult).collect(Collectors.toList())
        );
    }

    @ApiOperation(value = "添加设备", notes = "添加设备")
    @PostMapping("/access/v1/hr/device/add")
    @APILog
    public JSONResult deviceAddV1(@RequestBody @Valid DeviceInsertParam insertParam){

        DeviceInsertParamDTO dtoParam = new DeviceInsertParamDTO();
        dtoParam.setClientId(SecurityContextHolder.getUserDetails().getId());
        dtoParam.setDeviceId(insertParam.getDeviceId());
        dtoParam.setDeviceName(insertParam.getDeviceName());
        dtoParam.setWearer(insertParam.getWearer());
        dtoParam.setPolice(insertParam.getPolice());

        deviceService.insertDevice(dtoParam);
        return JSONResult.ok();

    }

    @ApiOperation(value = "设备详情", notes = "设备详情")
    @GetMapping("/access/v1/hr/device/details/{id}")
    @APILog
    public JSONResult<DeviceDetailResult> deviceDetailsV1(@PathVariable Long id){

        return JSONResult.ok(deviceToDetailResult(deviceService.findDeviceById(id)));

    }


    @ApiOperation(value = "修改设备", notes = "修改设备")
    @PostMapping("/v1/hr/device/update/{id}")
    @APILog
    public JSONResult deviceUpdateV1(@PathVariable Long id, @RequestBody @Valid DeviceUpdateParam updateParam){

        DeviceUpdateParamDTO dtoParam = new DeviceUpdateParamDTO();
        dtoParam.setWearer(updateParam.getWearer());
        dtoParam.setPolice(updateParam.getPolice());
        dtoParam.setDeviceName(updateParam.getDeviceName());

        deviceService.updateDevice(id, dtoParam);
        return JSONResult.ok();

    }


    @ApiOperation(value = "批量删除设备", notes = "批量删除设备")
    @PostMapping("/access/v1/hr/device/del")
    @APILog
    public JSONResult deviceDeleteV1(@RequestParam @ApiParam(value = "id用,隔开") String ids){

        for (String s : ids.split(",")) {
            deviceService.removeDeviceById(Long.parseLong(s));
        }
        return JSONResult.ok();

    }

    /**
     * 设备Result转换
     * @param device    设备
     */
    private DeviceQueryResult deviceToResult(Device device){

        DeviceQueryResult result = new DeviceQueryResult();
        result.setId(device.getId());
        result.setCreateDate(device.getCreateDate());
        result.setDeviceName(device.getDeviceName());
        result.setDeviceId(device.getDeviceId());
        result.setWearer(device.getWearer());
        result.setPolice(device.getPolice());
        result.setClientId(device.getClientId());

        HeartRate heartRate = heartRateService.findNewestByDeviceId(device.getDeviceId());

        if(heartRate != null){
            result.setLabelStatus(heartRate.getLabelStatus());
            result.setMeans(heartRate.getMeans());
        }

        return result;

    }

    /**
     * 设备DetailResult转换
     * @param device    设备
     */
    private DeviceDetailResult deviceToDetailResult(Device device){

        DeviceDetailResult result = new DeviceDetailResult();
        result.setId(device.getId());
        result.setCreateDate(device.getCreateDate());
        result.setDeviceName(device.getDeviceName());
        result.setDeviceId(device.getDeviceId());
        result.setWearer(device.getWearer());
        result.setPolice(device.getPolice());
        result.setClientId(device.getClientId());

        HeartRate heartRate = heartRateService.findNewestByDeviceId(device.getDeviceId());

        if(heartRate != null){
            result.setLabelStatus(heartRate.getLabelStatus());
            result.setMeans(heartRate.getMeans());
            result.setHrValue(heartRate.getHrValue());
        }
        result.setPoliceCount(policeService.countPoliceByDeviceId(device.getDeviceId()));

        return result;
    }


}
