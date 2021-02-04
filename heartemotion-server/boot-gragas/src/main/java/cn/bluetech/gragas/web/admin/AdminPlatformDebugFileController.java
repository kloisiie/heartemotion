package cn.bluetech.gragas.web.admin;

import java.lang.*;
import java.nio.charset.Charset;
import java.util.*;

import cn.hutool.http.HttpUtil;
import com.brframework.commonweb.json.PageParam;
import cn.bluetech.gragas.entity.hr.*;
import cn.bluetech.gragas.pojo.hr.*;
import cn.bluetech.gragas.service.hr.*;
import cn.bluetech.gragas.json.admin.hr.*;
import com.google.common.collect.Lists;
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
 * 平台调试文件模块
 * @author xu
 * @date 2020-12-16 21:51:27
 */ 
@RestController
@Api(tags = "平台调试文件模块")
@RequestMapping("/admin/access")
@Slf4j
public class AdminPlatformDebugFileController{

    /** 平台调试文件Service */
    @Autowired
    public PlatformDebugFileService platformDebugFileService;


    
    @ApiOperation(value = "平台调试文件列表", notes = "平台调试文件列表")
    @GetMapping("/v1/hr/platform-debug-file/list")
    @PreAuthorize("hasRole('hr-platform-debug-file-query')")
    public JSONResult<Page<PlatformDebugFileQueryResult>> platformDebugFilePageV1(PageParam pageParam, PlatformDebugFilePageQueryParam queryParam){
        
        PlatformDebugFilePageQueryParamDTO dtoParam = new PlatformDebugFilePageQueryParamDTO();
            dtoParam.setFileName(queryParam.getFileName());
    
        return JSONResult.ok(jpaPageHandleToPage(
                platformDebugFileService.findPlatformDebugFilePage(pageParam, dtoParam), pageParam,
                this::platformDebugFileToResult
        ));
            
    }


    
    @ApiOperation(value = "平台调试文件详情", notes = "平台调试文件详情")
    @GetMapping("/v1/hr/platform-debug-file/details/{id}")
    @PreAuthorize("hasRole('hr-platform-debug-file-query')")
    public JSONResult<PlatformDebugFileDetailResult> platformDebugFileDetailsV1(@PathVariable Long id){
        
        return JSONResult.ok(platformDebugFileToDetailResult(platformDebugFileService.findPlatformDebugFileById(id)));
            
    }


    
    @ApiOperation(value = "添加平台调试文件", notes = "添加平台调试文件")
    @PostMapping("/v1/hr/platform-debug-file/add")
    @PreAuthorize("hasRole('hr-platform-debug-file-add')")
    public JSONResult platformDebugFileAddV1(@Valid PlatformDebugFileInsertParam insertParam){
        
        PlatformDebugFileInsertParamDTO dtoParam = new PlatformDebugFileInsertParamDTO();
        dtoParam.setFileName(insertParam.getFileName());
        dtoParam.setDetails(insertParam.getDetails());
        String content = HttpUtil.downloadString(insertParam.getContent().get(0), Charset.forName("UTF-8"));
        dtoParam.setContent(content);
    
        platformDebugFileService.insertPlatformDebugFile(dtoParam);
        return JSONResult.ok();
            
    }


    
    @ApiOperation(value = "修改平台调试文件", notes = "修改平台调试文件")
    @PostMapping("/v1/hr/platform-debug-file/update/{id}")
    @PreAuthorize("hasRole('hr-platform-debug-file-update')")
    public JSONResult platformDebugFileUpdateV1(@PathVariable Long id, @Valid PlatformDebugFileUpdateParam updateParam){
        
        PlatformDebugFileUpdateParamDTO dtoParam = new PlatformDebugFileUpdateParamDTO();
        dtoParam.setFileName(updateParam.getFileName());
        dtoParam.setDetails(updateParam.getDetails());
        String content = HttpUtil.downloadString(updateParam.getContent().get(0), Charset.forName("UTF-8"));
        dtoParam.setContent(content);
    
        platformDebugFileService.updatePlatformDebugFile(id, dtoParam);
        return JSONResult.ok();
            
    }


    
    @ApiOperation(value = "删除平台调试文件", notes = "删除平台调试文件")
    @DeleteMapping("/v1/hr/platform-debug-file/del/{id}")
    @PreAuthorize("hasRole('hr-platform-debug-file-del')")
    public JSONResult platformDebugFileDeleteV1(@PathVariable Long id){
        
        platformDebugFileService.removePlatformDebugFileById(id);
        return JSONResult.ok();
            
    }

    @ApiOperation(value = "批量删除平台调试文件", notes = "批量删除平台调试文件")
    @DeleteMapping("/v1/hr/platform-debug-file/batch-del")
    @PreAuthorize("hasRole('hr-platform-debug-file-del')")
    public JSONResult platformDebugFileBatchDeleteV1(@RequestParam("ids") List<Long> ids){

        for (Long id : ids) {
            platformDebugFileService.removePlatformDebugFileById(id);
        }

        return JSONResult.ok();

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


    /**
     * 平台调试文件DetailResult转换 
     * @param platformDebugFile    平台调试文件 
     */
    private PlatformDebugFileDetailResult platformDebugFileToDetailResult(PlatformDebugFile platformDebugFile){
        
        PlatformDebugFileDetailResult result = new PlatformDebugFileDetailResult();
        result.setId(platformDebugFile.getId());
        result.setCreateDate(platformDebugFile.getCreateDate());
        result.setFileName(platformDebugFile.getFileName());
        result.setContent(Lists.newArrayList());
        result.setDetails(platformDebugFile.getDetails());
        result.setStartTime(platformDebugFile.getStartTime());
        result.setEndTime(platformDebugFile.getEndTime());
    
        return result;
            
    }




}