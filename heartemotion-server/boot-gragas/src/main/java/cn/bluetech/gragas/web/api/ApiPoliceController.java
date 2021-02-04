package cn.bluetech.gragas.web.api;

import cn.bluetech.gragas.aop.annotation.APILog;
import cn.bluetech.gragas.entity.hr.Police;
import cn.bluetech.gragas.json.admin.hr.PolicePageQueryParam;
import cn.bluetech.gragas.json.admin.hr.PolicePollQueryParam;
import cn.bluetech.gragas.json.admin.hr.PoliceQueryResult;
import cn.bluetech.gragas.json.api.hr.PolicePollResult;
import cn.bluetech.gragas.pojo.hr.PolicePageQueryParamDTO;
import cn.bluetech.gragas.service.hr.PoliceService;
import com.brframework.commonweb.json.JSONResult;
import com.brframework.commonweb.json.Page;
import com.brframework.commonweb.json.PageParam;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.brframework.commondb.core.ControllerPageHandle.jpaPageHandleToPage;

/**
 * @author xu
 * @date 2020/12/16 21:27
 */
@RestController
@Api(tags = "报警记录模块")
@RequestMapping("/api")
@Slf4j
public class ApiPoliceController {


    /** 报警记录Service */
    @Autowired
    public PoliceService policeService;



    @ApiOperation(value = "报警记录列表", notes = "报警记录列表")
    @GetMapping("/access/v1/hr/police/list")
    @APILog
    public JSONResult<Page<PoliceQueryResult>> policePageV1(PageParam pageParam, PolicePageQueryParam queryParam){

        PolicePageQueryParamDTO dtoParam = new PolicePageQueryParamDTO();
        dtoParam.setDeviceId(queryParam.getDeviceId());

        return JSONResult.ok(jpaPageHandleToPage(
                policeService.findPolicePage(pageParam, dtoParam), pageParam,
                this::policeToResult
        ));

    }


    @ApiOperation(value = "报警记录列表(轮询)", notes = "报警记录列表(轮询)")
    @GetMapping("/access/v1/hr/police/poll")
    public JSONResult<PolicePollResult> policePollV1(PolicePollQueryParam queryParam){
        //查询某些设备下，在固定时间区间产生的报警记录
        PolicePollResult result = new PolicePollResult();
        LocalDateTime start;
        LocalDateTime end;

        if(queryParam.getTimeMark() == null){
            result.setPolices(Lists.newArrayList());
            result.setTimeMark(LocalDateTime.now());
            return JSONResult.ok(result);
        } else {
            end = LocalDateTime.now();
            start = queryParam.getTimeMark();

            result.setTimeMark(end);
            List<PoliceQueryResult> collect = policeService.listPoliceByPoll(queryParam.getDeviceIds().split(","), start, end)
                    .stream()
                    .map(this::policeToResult)
                    .collect(Collectors.toList());
            result.setPolices(collect);

            return JSONResult.ok(result);

        }
    }


    /**
     * 报警记录Result转换
     * @param police    报警记录
     */
    private PoliceQueryResult policeToResult(Police police){

        PoliceQueryResult result = new PoliceQueryResult();
        result.setId(police.getId());
        result.setCreateDate(police.getCreateDate());
        result.setPoliceTime(police.getPoliceTime());
        result.setDeviceId(police.getDeviceId());

        return result;

    }
}
