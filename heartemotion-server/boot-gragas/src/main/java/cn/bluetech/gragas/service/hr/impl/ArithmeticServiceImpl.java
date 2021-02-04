package cn.bluetech.gragas.service.hr.impl;

import java.lang.*;
import java.util.*;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

import cn.bluetech.gragas.globals.HeartRateConstant;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.brframework.common.utils.DateTimeUtils;
import com.brframework.commonweb.exception.HandleException;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.vavr.Tuple2;
import org.springframework.data.domain.*;
import com.brframework.commonweb.json.PageParam;
import cn.bluetech.gragas.dao.hr.*;
import cn.bluetech.gragas.entity.hr.*;
import cn.bluetech.gragas.pojo.hr.*;
import cn.bluetech.gragas.service.hr.*;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.extern.slf4j.Slf4j;
import com.brframework.commondb.core.ExQuery;
import org.springframework.transaction.annotation.Transactional;
import com.querydsl.jpa.impl.JPAQueryFactory;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import com.querydsl.core.QueryResults;


/**
 * 算法服务实现
 * @author xu
 * @date 2020-12-16 16:37:39
 */ 
@Service
@Slf4j
public class ArithmeticServiceImpl implements ArithmeticService{

    /** 算法Dao */
    @Autowired
    private ArithmeticDao arithmeticDao;
    /** QueryDSL查询 */
    private JPAQueryFactory queryFactory;


    /**
     * 设置QueryFactory 
     * @param entityManager    entity manager 
     */
    @Resource
    public void setQueryFactory(@Autowired EntityManager entityManager){
        
        queryFactory = new JPAQueryFactory(entityManager);
            
    }


    /**
     * 通过ID查询算法 
     * @param arithmeticId    算法的ID 
     * @return 算法
     */
    @Override
    public Arithmetic findArithmeticById(Long arithmeticId){
        
        return arithmeticDao.findById(arithmeticId).orElse(null);
            
    }

    @Override
    public List<ArithmeticExecuteDTO> execute(Long arithmeticId, List<Map<String, Object>> list) {

        Arithmetic arithmetic = findArithmeticById(arithmeticId);

        String serverUrl = arithmetic.getServerUrl().endsWith("/") ?
                arithmetic.getServerUrl() : arithmetic.getServerUrl() + "/";

        String response = HttpUtil.post(serverUrl + "arithmetic/execute", JSON.toJSONString(list));
        JSONArray array = JSON.parseArray(response);

        List<ArithmeticExecuteDTO> results = Lists.newArrayList();
        for (int i = 0; i < array.size(); i++) {
            JSONObject jsonObject = array.getJSONObject(i);

            int status = jsonObject.getIntValue("status");
            String means = jsonObject.getString("means");

            if(status == -1){
                throw new HandleException("算法调用错误");
            }

            ArithmeticExecuteDTO executeDTO = new ArithmeticExecuteDTO();
            executeDTO.setStatus(1);
            executeDTO.setLabelStatus(HeartRateConstant.LabelStatus.values()[status]);
            executeDTO.setMeans(means);
            results.add(executeDTO);
        }

        return results;
    }

    @Override
    public ArithmeticExecuteDTO executeStandard(String taskId, LocalDateTime hrTime, Integer hrValue) {
        Arithmetic arithmetic = arithmeticDao.findOne(ExQuery.eq(QArithmetic.arithmetic.type,
                HeartRateConstant.ArithmeticType.STANDARD)).orElse(null);
        if(arithmetic == null){
            throw new HandleException("不存在标准算法文件");
        }

        Map<String, Object> body = Maps.newHashMap();
        body.put("taskId", taskId);
        body.put("time", DateTimeUtils.toEpochSecond(hrTime));
        body.put("hr", hrValue);

        return execute(arithmetic.getId(), Lists.newArrayList(body)).get(0);
    }


    /**
     * 分页查询算法 
     * @param queryParam    查询参数
     * @param pageParam    分页参数 
     * @return 算法分页
     */
    @Override
    public Page<Arithmetic> findArithmeticPage(PageParam pageParam, ArithmeticPageQueryParamDTO queryParam){
        
        QueryResults<Arithmetic> results = queryFactory.select(QArithmetic.arithmetic)
            .from(QArithmetic.arithmetic)
            .where(ExQuery.booleanExpressionBuilder()
                .and(ExQuery.like(QArithmetic.arithmetic.name, queryParam.getName()))
                .build())
            .offset(pageParam.getPageIndex() * pageParam.getPageSize())
            .limit(pageParam.getPageSize())
            .orderBy(QArithmetic.arithmetic.sort.asc())
            .fetchResults();
    
        return new PageImpl<>(results.getResults(), Pageable.unpaged(), results.getTotal());
            
    }

    @Override
    public List<Arithmetic> listArithmetic() {
        QueryResults<Arithmetic> results = queryFactory.select(QArithmetic.arithmetic)
                .from(QArithmetic.arithmetic)
                .orderBy(QArithmetic.arithmetic.sort.asc())
                .fetchResults();
        return results.getResults();
    }


    /**
     * 添加算法 
     * @param insertParam    添加参数 
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertArithmetic(ArithmeticInsertParamDTO insertParam){

        if(insertParam.getType().equals(HeartRateConstant.ArithmeticType.STANDARD)){
            Arithmetic standard = arithmeticDao.findOne(ExQuery.eq(QArithmetic.arithmetic.type,
                    HeartRateConstant.ArithmeticType.STANDARD)).orElse(null);
            if(standard != null){
                throw new HandleException("已存在基准算法");
            }
        }


        
        Arithmetic arithmetic = new Arithmetic();
        arithmetic.setCreateDate(LocalDateTime.now());
        arithmetic.setName(insertParam.getName());
        arithmetic.setServerUrl(insertParam.getServerUrl());
        arithmetic.setType(insertParam.getType());
        arithmetic.setSort(insertParam.getSort());

        arithmeticDao.save(arithmetic);
            
    }


    /**
     * 修改算法 
     * @param arithmeticId    算法的ID
     * @param updateParam    修改参数 
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateArithmetic(Long arithmeticId, ArithmeticUpdateParamDTO updateParam){
        
        Arithmetic arithmetic = findArithmeticById(arithmeticId);
        arithmetic.setName(updateParam.getName());
        arithmetic.setServerUrl(updateParam.getServerUrl());
        arithmetic.setSort(updateParam.getSort());
    
        arithmeticDao.save(arithmetic);
            
    }


    /**
     * 删除算法 
     * @param arithmeticId    算法的ID 
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeArithmeticById(Long arithmeticId){
        
        Arithmetic arithmetic = findArithmeticById(arithmeticId);
        arithmeticDao.delete(arithmetic);
    }




}