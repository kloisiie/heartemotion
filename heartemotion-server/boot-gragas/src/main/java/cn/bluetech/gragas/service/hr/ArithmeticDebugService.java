package cn.bluetech.gragas.service.hr;

import java.lang.*;
import java.util.*;
import cn.bluetech.gragas.entity.hr.*;
import cn.bluetech.gragas.json.api.hr.DebugExecuteArithmeticResult;
import org.springframework.data.domain.Page;
import com.brframework.commonweb.json.PageParam;
import cn.bluetech.gragas.pojo.hr.*;


/**
 * 算法调试服务实现
 * @author xu
 * @date 2020-12-16 16:50:51
 */ 
public interface ArithmeticDebugService{



    /**
     * 通过ID查询算法调试 
     * @param arithmeticDebugId    算法调试的ID 
     * @return 算法调试
     */
     ArithmeticDebug findArithmeticDebugById(Long arithmeticDebugId);


    /**
     * 分页查询算法调试 
     * @param queryParam    查询参数
     * @param pageParam    分页参数 
     * @return 算法调试分页
     */
     Page<ArithmeticDebug> findArithmeticDebugPage(PageParam pageParam, ArithmeticDebugPageQueryParamDTO queryParam);


    /**
     * 添加算法调试 
     * @param insertParam    添加参数 
     */
     String insertArithmeticDebug(ArithmeticDebugInsertParamDTO insertParam);


    /**
     * 算法调试结果
     * @param taskId        任务id
     */
    DebugExecuteArithmeticResult debugResult(String taskId);


    /**
     * 修改算法调试 
     * @param updateParam    修改参数
     * @param arithmeticDebugId    算法调试的ID 
     */
     void updateArithmeticDebug(Long arithmeticDebugId, ArithmeticDebugUpdateParamDTO updateParam);


    /**
     * 删除算法调试 
     * @param arithmeticDebugId    算法调试的ID 
     */
     void removeArithmeticDebugById(Long arithmeticDebugId);




}