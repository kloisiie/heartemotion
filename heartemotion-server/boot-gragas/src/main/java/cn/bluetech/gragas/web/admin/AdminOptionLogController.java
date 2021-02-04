package cn.bluetech.gragas.web.admin;

import java.lang.*;
import java.util.*;
import com.brframework.commonweb.json.PageParam;
import cn.bluetech.gragas.entity.client.*;
import cn.bluetech.gragas.pojo.client.*;
import cn.bluetech.gragas.service.client.*;
import cn.bluetech.gragas.json.admin.client.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.extern.slf4j.Slf4j;
import com.brframework.commonweb.json.JSONResult;
import com.brframework.commonweb.json.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import javax.validation.Valid;
import static com.brframework.commondb.core.ControllerPageHandle.*;


/**
 * 操作日志模块
 * @author xu
 * @date 2020-12-16 17:40:36
 */ 
@RestController
@Api(tags = "操作日志模块")
@RequestMapping("/admin/access")
@Slf4j
public class AdminOptionLogController{

    /** 操作日志Service */
    @Autowired
    public OptionLogService optionLogService;


    
    @ApiOperation(value = "操作日志列表", notes = "操作日志列表")
    @GetMapping("/v1/client/option-log/list")
    @PreAuthorize("hasRole('client-option-log-query')")
    public JSONResult<Page<OptionLogQueryResult>> optionLogPageV1(PageParam pageParam, OptionLogPageQueryParam queryParam){
        
        OptionLogPageQueryParamDTO dtoParam = new OptionLogPageQueryParamDTO();
        if(queryParam.getCreateDate() != null && queryParam.getCreateDate().size() > 0){
            dtoParam.setCreateDateStart(queryParam.getCreateDate().get(0));
            dtoParam.setCreateDateEnd(queryParam.getCreateDate().get(1));
        }

        dtoParam.setClientId(queryParam.getClientId());
        dtoParam.setCallApi(queryParam.getCallApi());
        dtoParam.setIp(queryParam.getIp());
    
        return JSONResult.ok(jpaPageHandleToPage(
                optionLogService.findOptionLogPage(pageParam, dtoParam), pageParam,
                this::optionLogToResult
        ));
            
    }


    @ApiOperation(value = "删除操作日志", notes = "删除操作日志")
    @DeleteMapping("/v1/client/option-log/del/{id}")
    @PreAuthorize("hasRole('client-option-log-del')")
    public JSONResult optionLogDeleteV1(@PathVariable Long id){

        optionLogService.removeOptionLogById(id);
        return JSONResult.ok();

    }


    @ApiOperation(value = "批量删除操作日志", notes = "批量删除操作日志")
    @DeleteMapping("/v1/client/option-log/batch-del")
    @PreAuthorize("hasRole('client-option-log-del')")
    public JSONResult optionLogBatchDeleteV1(@RequestParam("ids") List<Long> ids){

        for (Long id : ids) {
            optionLogService.removeOptionLogById(id);
        }


        return JSONResult.ok();

    }


    /**
     * 操作日志Result转换 
     * @param optionLog    操作日志 
     */
    private OptionLogQueryResult optionLogToResult(OptionLog optionLog){
        
        OptionLogQueryResult result = new OptionLogQueryResult();
        result.setId(optionLog.getId());
        result.setCreateDate(optionLog.getCreateDate());
        result.setClientId(optionLog.getClientId());
        result.setCallApi(optionLog.getCallApi());
        result.setIp(optionLog.getIp());
    
        return result;
            
    }





}