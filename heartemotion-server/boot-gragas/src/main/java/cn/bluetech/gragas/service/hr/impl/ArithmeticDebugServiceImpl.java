package cn.bluetech.gragas.service.hr.impl;

import java.lang.*;
import java.time.ZoneId;
import java.util.*;
import java.time.LocalDateTime;
import java.util.concurrent.*;

import cn.bluetech.gragas.globals.HeartRateConstant;
import cn.bluetech.gragas.json.api.hr.DebugExecuteArithmeticEntryResult;
import cn.bluetech.gragas.json.api.hr.DebugExecuteArithmeticResult;
import cn.bluetech.gragas.json.api.hr.HeartRateExportResult;
import cn.hutool.core.util.NumberUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.brframework.common.utils.DateTimeUtils;
import com.brframework.commonweb.exception.HandleException;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.vavr.Tuple2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;
import com.brframework.commonweb.json.PageParam;
import cn.bluetech.gragas.dao.hr.*;
import cn.bluetech.gragas.entity.hr.*;
import cn.bluetech.gragas.pojo.hr.*;
import cn.bluetech.gragas.service.hr.*;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.extern.slf4j.Slf4j;
import com.brframework.commondb.utils.QueryUtils;
import com.brframework.commondb.core.ExQuery;
import org.springframework.transaction.annotation.Transactional;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import com.querydsl.core.QueryResults;


/**
 * 算法调试服务实现
 * @author xu
 * @date 2020-12-16 16:50:51
 */ 
@Service
@Slf4j
public class ArithmeticDebugServiceImpl implements ArithmeticDebugService{

    /** 算法调试Dao */
    @Autowired
    private ArithmeticDebugDao arithmeticDebugDao;
    /** QueryDSL查询 */
    private JPAQueryFactory queryFactory;

    @Autowired
    private ArithmeticService arithmeticService;

    private BlockingQueue<Runnable> workQueue;
    private ExecutorService executorService;

    private static final int queueCount = 10;
    private static final int threadCount = 3;

    /**
     * 设置QueryFactory 
     * @param entityManager    entity manager 
     */
    @Resource
    public void setQueryFactory(@Autowired EntityManager entityManager){
        
        queryFactory = new JPAQueryFactory(entityManager);
            
    }

    @PostConstruct
    public void init(){
        workQueue = new ArrayBlockingQueue<>(queueCount);
        executorService = new ThreadPoolExecutor(threadCount, threadCount,
                0L, TimeUnit.MILLISECONDS, workQueue);
    }


    /**
     * 通过ID查询算法调试 
     * @param arithmeticDebugId    算法调试的ID 
     * @return 算法调试
     */
    @Override
    public ArithmeticDebug findArithmeticDebugById(Long arithmeticDebugId){
        
        return arithmeticDebugDao.findById(arithmeticDebugId).orElse(null);
            
    }


    /**
     * 分页查询算法调试 
     * @param queryParam    查询参数
     * @param pageParam    分页参数 
     * @return 算法调试分页
     */
    @Override
    public Page<ArithmeticDebug> findArithmeticDebugPage(PageParam pageParam, ArithmeticDebugPageQueryParamDTO queryParam){
        
        QueryResults<ArithmeticDebug> results = queryFactory.select(QArithmeticDebug.arithmeticDebug)
            .from(QArithmeticDebug.arithmeticDebug)
            .where(ExQuery.booleanExpressionBuilder()
    
                .build())
            .offset(pageParam.getPageIndex() * pageParam.getPageSize())
            .limit(pageParam.getPageSize())
            .orderBy(QArithmeticDebug.arithmeticDebug.createDate.desc())
            .fetchResults();
    
        return new PageImpl<>(results.getResults(), Pageable.unpaged(), results.getTotal());
            
    }


    /**
     * 添加算法调试 
     * @param insertParam    添加参数 
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String insertArithmeticDebug(ArithmeticDebugInsertParamDTO insertParam){
        
        ArithmeticDebug arithmeticDebug = new ArithmeticDebug();
        arithmeticDebug.setCreateDate(LocalDateTime.now());
        arithmeticDebug.setStandardResult(insertParam.getStandardResult());
        arithmeticDebug.setArithmeticId(insertParam.getArithmeticId());
        arithmeticDebug.setClientId(insertParam.getClientId());
        arithmeticDebug.setStatus(HeartRateConstant.DebugStatus.RUNNING);
    
        arithmeticDebugDao.save(arithmeticDebug);
        String taskId = arithmeticDebug.getId().toString();

        executorService.submit(new ArithmeticDebugRunnable(taskId));

        return taskId;
    }

    @Override
    public DebugExecuteArithmeticResult debugResult(String taskId) {
        ArithmeticDebug arithmeticDebug = findArithmeticDebugById(Long.parseLong(taskId));

        if(arithmeticDebug == null){
            throw new HandleException("不存在该任务");
        }

        if(arithmeticDebug.getStatus() == HeartRateConstant.DebugStatus.RUNNING){
            DebugExecuteArithmeticResult result = new DebugExecuteArithmeticResult();
            result.setStatus(1);
            return result;
        }

        if(arithmeticDebug.getStatus() == HeartRateConstant.DebugStatus.DEFEATED){
            DebugExecuteArithmeticResult result = new DebugExecuteArithmeticResult();
            result.setStatus(3);
            return result;
        }

        List<HeartRateExportResult> standardResult = JSON.parseObject(arithmeticDebug.getStandardResult(),
                new TypeReference<List<HeartRateExportResult>>() {
        });

        List<HeartRateExportResult> debugResult = JSON.parseObject(arithmeticDebug.getDebugResult(),
                new TypeReference<List<HeartRateExportResult>>() {
        });

        int sumCorrectCount = 0;
        for (int i = 0; i < debugResult.size(); i++) {
            if(debugResult.get(i).getLabelStatus() == standardResult.get(i).getLabelStatus()){
                sumCorrectCount ++;
            }
        }

        Tuple2<Integer, Integer> noMoodRate = handleRate(standardResult, debugResult, 0);
        Tuple2<Integer, Integer> calmnessRate = handleRate(standardResult, debugResult, 1);
        Tuple2<Integer, Integer> agitatedRate = handleRate(standardResult, debugResult, 2);
        Tuple2<Integer, Integer> happyRate = handleRate(standardResult, debugResult, 3);

        List<DebugExecuteArithmeticEntryResult> enters = Lists.newArrayList();
        for (int i = 0; i < debugResult.size(); i++) {
            DebugExecuteArithmeticEntryResult entry = new DebugExecuteArithmeticEntryResult();
            entry.setHrTime(new Date(debugResult.get(i).getHrTime() * 1000)
                    .toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
            entry.setHrValue(debugResult.get(i).getHrValue());
            entry.setStandard(HeartRateConstant.LabelStatus.values()[standardResult.get(i).getLabelStatus()]);
            entry.setNormal(HeartRateConstant.LabelStatus.values()[debugResult.get(i).getLabelStatus()]);
            enters.add(entry);
        }


        DebugExecuteArithmeticResult result = new DebugExecuteArithmeticResult();
        result.setStatus(2);
        result.setSumRate(NumberUtil.div(sumCorrectCount, standardResult.size(), 2));
        if(noMoodRate._1() != 0){
            result.setNoMoodRate(NumberUtil.div(noMoodRate._2(), noMoodRate._1(), 2).doubleValue());
        } else {
            result.setNoMoodRate(0D);
        }

        if(calmnessRate._1() != 0){
            result.setCalmnessRate(NumberUtil.div(calmnessRate._2(), calmnessRate._1(), 2).doubleValue());
        } else {
            result.setCalmnessRate(0D);
        }

        if(agitatedRate._1() != 0){
            result.setAgitatedRate(NumberUtil.div(agitatedRate._2(), agitatedRate._1(), 2).doubleValue());
        } else {
            result.setAgitatedRate(0D);
        }

        if(happyRate._1() != 0){
            result.setHappyRate(NumberUtil.div(happyRate._2(), happyRate._1(), 2).doubleValue());
        } else {
            result.setHappyRate(0D);
        }


        result.setEnters(enters);

        return result;
    }


    private Tuple2<Integer, Integer> handleRate(List<HeartRateExportResult> standardResult,
                                                List<HeartRateExportResult> debugResult,
                                                int status){
        int count = 0;
        int correctCount = 0;
        for (int i = 0; i < debugResult.size(); i++) {
            if(standardResult.get(i).getLabelStatus() == status){
                count ++;

                if(debugResult.get(i).getLabelStatus() == standardResult.get(i).getLabelStatus()){
                    correctCount ++;
                }
            }
        }
        return new Tuple2<>(count, correctCount);
    }


    /**
     * 修改算法调试 
     * @param updateParam    修改参数
     * @param arithmeticDebugId    算法调试的ID 
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateArithmeticDebug(Long arithmeticDebugId, ArithmeticDebugUpdateParamDTO updateParam){
        
        ArithmeticDebug arithmeticDebug = findArithmeticDebugById(arithmeticDebugId);
        arithmeticDebug.setDebugResult(updateParam.getDebugResult());
        arithmeticDebug.setStatus(updateParam.getStatus());
    
        arithmeticDebugDao.save(arithmeticDebug);
            
    }


    /**
     * 删除算法调试 
     * @param arithmeticDebugId    算法调试的ID 
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeArithmeticDebugById(Long arithmeticDebugId){
        
        ArithmeticDebug arithmeticDebug = findArithmeticDebugById(arithmeticDebugId);
        arithmeticDebugDao.delete(arithmeticDebug);
            
    }


    class ArithmeticDebugRunnable extends ExecutorRunnable {
        private String taskId;

        public ArithmeticDebugRunnable(String taskId){
            this.taskId = taskId;
        }

        @Override
        protected void exception(Throwable e) {
            super.exception(e);
            ArithmeticDebug arithmeticDebug = findArithmeticDebugById(Long.parseLong(taskId));
            arithmeticDebug.setStatus(HeartRateConstant.DebugStatus.DEFEATED);
            arithmeticDebugDao.save(arithmeticDebug);
        }

        @Override
        protected void doRun() {
            ArithmeticDebug arithmeticDebug = findArithmeticDebugById(Long.parseLong(taskId));
            Long arithmeticId = arithmeticDebug.getArithmeticId();
            String standardResult = arithmeticDebug.getStandardResult();
            List<HeartRateExportResult> results = JSON.parseObject(standardResult, new TypeReference<List<HeartRateExportResult>>() {
            });


            List<Map<String, Object>> params = Lists.newArrayList();

            for (HeartRateExportResult result : results) {
                Map<String, Object> body = Maps.newHashMap();
                body.put("taskId", taskId);
                body.put("time", result.getHrTime());
                body.put("hr", result.getHrValue());
                params.add(body);
            }


            List<ArithmeticExecuteDTO> list = arithmeticService.execute(arithmeticId, params);
            List<HeartRateExportResult> debugResults = Lists.newArrayList();
            for (int i = 0; i < list.size(); i++) {
                HeartRateExportResult debug = new HeartRateExportResult();
                debug.setHrTime(results.get(i).getHrTime());
                debug.setHrValue(results.get(i).getHrValue());
                debug.setLabelStatus(list.get(i).getLabelStatus().ordinal());
                debug.setMeans(list.get(i).getMeans());
                debug.setLabelType(1);
                debugResults.add(debug);
            }

            arithmeticDebug.setDebugResult(JSON.toJSONString(debugResults));
            arithmeticDebug.setStatus(HeartRateConstant.DebugStatus.SUCCESS);
            arithmeticDebugDao.save(arithmeticDebug);
        }
    }


}