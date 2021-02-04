package cn.bluetech.gragas.web.admin;

import cn.bluetech.gragas.json.admin.sys.SystemLogMessageRemoveConfigParam;
import cn.bluetech.gragas.json.admin.sys.*;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.brframework.common.utils.DateTimeUtils;
import com.brframework.commondb.core.ControllerPageHandle;
import com.brframework.commonweb.json.JSONResult;
import com.brframework.commonweb.json.Page;
import com.brframework.commonweb.json.PageParam;
import com.brframework.commonwebbase.service.DictionaryService;
import com.brframework.log.entity.LogMessage;
import com.brframework.log.pojo.LogMessageQueryDTO;
import com.brframework.log.pojo.LogMessageRemoveConfigDTO;
import com.brframework.log.pojo.LogMessageRemoveDTO;
import com.brframework.log.service.LogMessageService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * 系统检测
 * @author xu
 * @date 2020/5/14 15:24
 */
@RestController
@RequestMapping("/admin/access")
@Slf4j
public class AdminSystemMonitorController {

    @Autowired
    LogMessageService logMessageService;

    @Autowired
    DictionaryService dictionaryService;

    @ApiOperation(value = "系统运行日志", notes = "系统运行日志")
    @GetMapping("/v1/system/log/list")
    @PreAuthorize("hasRole('system-log-message')")
    public JSONResult<Page<SystemLogMessageResult>> systemLogList(PageParam pageParam
            , SystemLogMessageListParam param){
        LogMessageQueryDTO query = new LogMessageQueryDTO();
        if(param.getLevel() != null && param.getLevel().size() > 0){
            query.setLevel(param.getLevel().get(0));
        }

        query.setSearch(param.getSearch());
        query.setThreadName(param.getThreadName());
        query.setLoggerName(param.getLoggerName());
        if(param.getTimeBetween() != null && param.getTimeBetween().size() > 0){
            LocalDateTime timeStart = param.getTimeBetween().get(0);
            LocalDateTime timeEnd = param.getTimeBetween().get(1);
            query.setStartTime(DateTimeUtils.toEpochMilli(timeStart));
            query.setEndTime(DateTimeUtils.toEpochMilli(timeEnd));
        } else {

            query.setStartTime(DateTimeUtils.toEpochMilli(LocalDateTime.of(LocalDate.now(), LocalTime.MIN)));
            query.setEndTime(DateTimeUtils.toEpochMilli(LocalDateTime.of(LocalDate.now(), LocalTime.MAX)));
        }

        return JSONResult.ok(ControllerPageHandle.jpaPageHandleToPage(
                logMessageService.findLogPage(pageParam, query), pageParam, this::logMessageToResult));
    }

    @ApiOperation(value = "系统运行日志详情", notes = "系统运行日志详情")
    @GetMapping("/v1/system/log/{id}")
    @PreAuthorize("hasRole('system-log-message')")
    public JSONResult<SystemLogMessageResult> systemLogDetails(@PathVariable Long id){

        return JSONResult.ok(
                logMessageToResult(logMessageService.findLogById(id), true)
        );
    }
    private SystemLogMessageResult logMessageToResult(LogMessage logMessage){
        return logMessageToResult(logMessage, false);
    }
    private SystemLogMessageResult logMessageToResult(LogMessage logMessage, boolean format){
        SystemLogMessageResult result = new SystemLogMessageResult();
        result.setId(logMessage.getId());
        result.setTimeStampTime(new Date(logMessage.getTimeStamp()).toInstant()
                .atZone(ZoneId.systemDefault()).toLocalDateTime());
        result.setThreadName(logMessage.getThreadName());
        result.setLoggerName(logMessage.getLoggerName());
        result.setLevel(logMessage.getLevel());
        result.setIp(logMessage.getIp());
        result.setSynopsis(logMessage.getMessage().contains("\n") ?
                logMessage.getMessage().substring(0, logMessage.getMessage().indexOf("\n") + 1) : logMessage.getMessage());
        if(format){
            result.setMessage(format(logMessage.getMessage()));
        }
        return result;
    }

    @ApiOperation(value = "手动清理运行日志配置", notes = "手动清理运行日志配置")
    @PostMapping("/v1/system/log/remove")
    @PreAuthorize("hasRole('SYSTEM-LOG-MESSAGE-REMOVE')")
    public JSONResult systemLogRemove(@Valid @RequestBody SystemLogMessageRemoveParam param){
        LogMessageRemoveDTO removeDTO = new LogMessageRemoveDTO();
        String timeStart = param.getTimeBetween().split(",")[0];
        String timeEnd = param.getTimeBetween().split(",")[1];
        removeDTO.setStartTime(DateUtil.parse(timeStart, "yyyy-MM-dd HH:mm:ss").getTime());
        removeDTO.setEndTime(DateUtil.parse(timeEnd, "yyyy-MM-dd HH:mm:ss").getTime());
        removeDTO.setLevel(param.getLevel());
        removeDTO.setThreadName(param.getThreadName());
        removeDTO.setLoggerName(param.getLoggerName());

        logMessageService.removeConditionally(removeDTO);
        return JSONResult.ok();
    }

    @ApiOperation(value = "自动清理运行日志配置", notes = "自动清理运行日志配置")
    @PostMapping("/v1/system/log/remove/config")
    @PreAuthorize("hasRole('SYSTEM-LOG-MESSAGE-REMOVE-CONFIG')")
    public JSONResult systemLogRemoveConfig(@Valid @RequestBody SystemLogMessageRemoveConfigParam param){
        LogMessageRemoveConfigDTO configDTO = new LogMessageRemoveConfigDTO();
        configDTO.setLevel(param.getLevel());
        configDTO.setThreadName(param.getThreadName());
        configDTO.setLoggerName(param.getLoggerName());
        configDTO.setPeriod(param.getPeriod());

        dictionaryService.set(LogMessageRemoveConfigDTO.LOG_MESSAGE_REMOVE_CONFIG_KEY, JSONObject.toJSONString(configDTO));
        return JSONResult.ok();
    }

    @ApiOperation(value = "获取自动清理运行日志配置", notes = "获取自动清理运行日志配置")
    @GetMapping("/v1/system/log/remove/config")
    @PreAuthorize("hasRole('SYSTEM-LOG-MESSAGE-REMOVE-CONFIG')")
    public JSONResult<LogMessageRemoveConfigDTO> getSystemLogRemoveConfig(){
        LogMessageRemoveConfigDTO configDTO = JSON.parseObject(dictionaryService.get(LogMessageRemoveConfigDTO.LOG_MESSAGE_REMOVE_CONFIG_KEY), LogMessageRemoveConfigDTO.class);
        return JSONResult.ok(configDTO);
    }


    private static final String HTML_TEMP = "<!DOCTYPE html>\n" +
            "<html lang=\"en\">\n" +
            "<head>\n" +
            "    <meta charset=\"UTF-8\">\n" +
            "    <title>${title}</title>\n" +
            "</head>\n" +
            "<body>\n" +
            "${body}" +
            "</body>\n" +
            "</html>";

    private String format(String value){
        if(StringUtils.isEmpty(value)){
            return null;
        }
        StringBuilder body = new StringBuilder();
        body.append("<code>");
        for (String s : value.split("\n")) {
            body.append("<p>");
            boolean strong = s.contains("com.softwarebr") || s.contains("com.brframework") || s.contains("cn.bluetech");
            boolean caused = s.contains("Caused by:");
            if(strong){
                body.append("<strong style=\"color: #1A94E6\">");
            }
            if(caused){
                body.append("<strong style=\"color: #FF0000\">");
            }
            body.append(s.replace("\t", "&nbsp;&nbsp;&nbsp;&nbsp;"));
            if(caused){
                body.append("</strong>");
            }
            if(strong){
                body.append("</strong>");
            }
            body.append("</p>");
        }
        body.append("</code>");
        return HTML_TEMP.replace("${body}", body.toString());
    }
}
