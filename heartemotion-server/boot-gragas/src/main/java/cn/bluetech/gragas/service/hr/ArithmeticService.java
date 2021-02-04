package cn.bluetech.gragas.service.hr;

import java.lang.*;
import java.time.LocalDateTime;
import java.util.*;
import cn.bluetech.gragas.entity.hr.*;
import org.springframework.data.domain.Page;
import com.brframework.commonweb.json.PageParam;
import cn.bluetech.gragas.pojo.hr.*;


/**
 * 算法服务实现
 * @author xu
 * @date 2020-12-16 16:37:39
 */ 
public interface ArithmeticService{



    /**
     * 通过ID查询算法 
     * @param arithmeticId    算法的ID 
     * @return 算法
     */
     Arithmetic findArithmeticById(Long arithmeticId);

    /**
     * 执行算法
     * @param arithmeticId   算法ID
     * @param list           算法执行参数
     * @return  执行结果
     */
    List<ArithmeticExecuteDTO> execute(Long arithmeticId, List<Map<String, Object>> list);

    /**
     * 执行标准算法
     * @param taskId         任务ID
     * @param hrTime         心率时间
     * @param hrValue        心率值
     * @return  执行结果
     */
    ArithmeticExecuteDTO executeStandard(String taskId, LocalDateTime hrTime, Integer hrValue);


    /**
     * 分页查询算法 
     * @param queryParam    查询参数
     * @param pageParam    分页参数 
     * @return 算法分页
     */
     Page<Arithmetic> findArithmeticPage(PageParam pageParam, ArithmeticPageQueryParamDTO queryParam);

    /**
     * 算法列表
     * @return  算法列表
     */
    List<Arithmetic> listArithmetic();

    /**
     * 添加算法 
     * @param insertParam    添加参数 
     */
     void insertArithmetic(ArithmeticInsertParamDTO insertParam);


    /**
     * 修改算法 
     * @param arithmeticId    算法的ID
     * @param updateParam    修改参数 
     */
     void updateArithmetic(Long arithmeticId, ArithmeticUpdateParamDTO updateParam);


    /**
     * 删除算法 
     * @param arithmeticId    算法的ID 
     */
     void removeArithmeticById(Long arithmeticId);




}