package cn.bluetech.gragas.web.admin;

import java.lang.*;
import java.util.*;
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
 * 算法模块
 * @author xu
 * @date 2020-12-16 16:37:39
 */ 
@RestController
@Api(tags = "算法模块")
@RequestMapping("/admin/access")
@Slf4j
public class AdminArithmeticController{

    /** 算法Service */
    @Autowired
    public ArithmeticService arithmeticService;


    
    @ApiOperation(value = "算法列表", notes = "算法列表")
    @GetMapping("/v1/hr/arithmetic/list")
    @PreAuthorize("hasRole('hr-arithmetic-query')")
    public JSONResult<Page<ArithmeticQueryResult>> arithmeticPageV1(PageParam pageParam, ArithmeticPageQueryParam queryParam){
        
        ArithmeticPageQueryParamDTO dtoParam = new ArithmeticPageQueryParamDTO();
        dtoParam.setName(queryParam.getName());
        return JSONResult.ok(jpaPageHandleToPage(
                arithmeticService.findArithmeticPage(pageParam, dtoParam), pageParam,
                this::arithmeticToResult
        ));
            
    }


    
    @ApiOperation(value = "算法详情", notes = "算法详情")
    @GetMapping("/v1/hr/arithmetic/details/{id}")
    @PreAuthorize("hasRole('hr-arithmetic-query')")
    public JSONResult<ArithmeticDetailResult> arithmeticDetailsV1(@PathVariable Long id){
        
        return JSONResult.ok(arithmeticToDetailResult(arithmeticService.findArithmeticById(id)));
            
    }


    
    @ApiOperation(value = "添加算法", notes = "添加算法")
    @PostMapping("/v1/hr/arithmetic/add")
    @PreAuthorize("hasRole('hr-arithmetic-add')")
    public JSONResult arithmeticAddV1(@Valid ArithmeticInsertParam insertParam){
        
        ArithmeticInsertParamDTO dtoParam = new ArithmeticInsertParamDTO();
        dtoParam.setType(insertParam.getType());
        dtoParam.setName(insertParam.getName());
        dtoParam.setServerUrl(insertParam.getServerUrl());
    
        arithmeticService.insertArithmetic(dtoParam);
        return JSONResult.ok();
            
    }


    
    @ApiOperation(value = "修改算法", notes = "修改算法")
    @PostMapping("/v1/hr/arithmetic/update/{id}")
    @PreAuthorize("hasRole('hr-arithmetic-update')")
    public JSONResult arithmeticUpdateV1(@PathVariable Long id, @Valid ArithmeticUpdateParam updateParam){
        
        ArithmeticUpdateParamDTO dtoParam = new ArithmeticUpdateParamDTO();
        dtoParam.setName(updateParam.getName());
        dtoParam.setServerUrl(updateParam.getServerUrl());
    
        arithmeticService.updateArithmetic(id, dtoParam);
        return JSONResult.ok();
            
    }


    
    @ApiOperation(value = "删除算法", notes = "删除算法")
    @DeleteMapping("/v1/hr/arithmetic/del/{id}")
    @PreAuthorize("hasRole('hr-arithmetic-del')")
    public JSONResult arithmeticDeleteV1(@PathVariable Long id){
        
        arithmeticService.removeArithmeticById(id);
        return JSONResult.ok();
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
        result.setTypeName(arithmetic.getType().describe());
    
        return result;
            
    }


    /**
     * 算法DetailResult转换 
     * @param arithmetic    算法 
     */
    private ArithmeticDetailResult arithmeticToDetailResult(Arithmetic arithmetic){
        
        ArithmeticDetailResult result = new ArithmeticDetailResult();
        result.setId(arithmetic.getId());
        result.setCreateDate(arithmetic.getCreateDate());
        result.setName(arithmetic.getName());
        result.setServerUrl(arithmetic.getServerUrl());
        result.setType(arithmetic.getType());
    
        return result;
            
    }




}