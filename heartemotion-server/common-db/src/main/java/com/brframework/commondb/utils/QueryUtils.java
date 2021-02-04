package com.brframework.commondb.utils;

import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import com.brframework.common.utils.ConvertObjectUtil;
import com.brframework.common.utils.DateTimeUtils;
import com.brframework.commondb.annotation.QuerySort;
import com.brframework.commondb.annotation.param.ParamQuery;
import com.brframework.commondb.annotation.param.QueryExpression;
import com.brframework.commondb.core.CommonRepository;
import com.brframework.commondb.core.ExQuery;
import com.brframework.commonweb.exception.HandleException;
import com.brframework.commonweb.json.PageParam;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.lang.reflect.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 数据库查询工具
 * @author xu
 * @date 2020/3/8 1:08
 */
@Slf4j
public class QueryUtils {

    /**
     * 默认情况分页的排序字段
     */
    public static final String DEFAULT_PAGE_SORT_FIELD = "createDate";
    /**
     * 缓存ExQuery
     */
    private static Map<String, Method> exQueryMethod = null;


    /**
     * 默认情况下的分页排序
     * @return
     */
    public static final Sort defaultPageSort(){
        return new Sort(Sort.Direction.DESC, DEFAULT_PAGE_SORT_FIELD);
    }

    public static <DTO, Param> Page<DTO> page(CommonRepository repository, Class entityClass, PageParam pageParam,
                                                     Param param) {
        return page(repository, entityClass, pageParam, param, defaultPageSort());
    }

    public static <DTO, Param> Page<DTO> page(CommonRepository repository, Class entityClass, PageParam pageParam,
                                                     Param param, Sort sort) {
        PageRequest pageRequest = PageRequest.of(pageParam.getPageIndex(), pageParam.getPageSize(), sort);

        if(param == null){
            return repository.findAll(pageRequest);
        }

        return repository.findAll(genExpression(entityClass, param), pageRequest);

    }

    /**
     * 动态化的参数查询
     * @param queryFactory   JPA QueryDSL工厂
     * @param query          查询实体模板
     * @param exprs          查询的所有字段
     * @param pageParam      分页参数
     * @param param          查询条件
     * @param resultClass    返回类型
     * @param orderBy        排序方法
     * @return  返回分页数据
     */
    public static <DTO, Param> Page<DTO> selectPage(JPAQueryFactory queryFactory, EntityPathBase<?> query,
                                              List<Path<?>> exprs, PageParam pageParam,
                                              Param param, Class<DTO> resultClass, OrderSpecifier<?>... orderBy){
        QueryResults<Tuple> queryResults = queryFactory.select(exprs.toArray(new Expression[0]))
                .from(query).orderBy(orderBy)
                .where(genExpression(ClassUtil.loadClass(query.getType().getName()), param))
                .offset(pageParam.getPageIndex() * pageParam.getPageSize()).limit(pageParam.getPageSize()).fetchResults();
        List<DTO> list = queryResults.getResults().stream()
                .map(tuple -> tupleTo(tuple, query, exprs, resultClass))
                .collect(Collectors.toList());

        return new PageImpl<>(list, PageRequest.of(pageParam.getPageIndex(), pageParam.getPageSize()),
                queryResults.getTotal());
    }

    /**
     * 将Tuple转换成特定的对象
     * @param tuple           Tuple
     * @param query           查询实体模板
     * @param exprs           查询的所有字段
     * @param resultClass     返回类型
     * @return  返回特定的对象，转换异常的情况下将抛出异常
     */
    public static <Result> Result tupleTo(Tuple tuple, EntityPathBase<?> query, List<Path<?>> exprs,
                                          Class<Result> resultClass){
        try {
            Class queryClass = query.getClass();
            Result result = resultClass.newInstance();
            exprs.forEach(e -> {
                String fieldName = e.getMetadata().getName();
                try {

                    Field queryField = queryClass.getField(fieldName);
                    ReflectUtil.invoke(result, StrUtil.upperFirstAndAddPre(fieldName,
                            "set"), tuple.get((Expression) queryField.get(query)));
                } catch (Throwable e1) {
                    log.error(QueryUtils.class.getName() ,e1);
                }
            });
            return result;
        } catch (Throwable e){
            log.error(QueryUtils.class.getName() ,e);
            throw new RuntimeException(e);
        }

    }

    /**
     * 生成参数表达式
     * @param param
     * @return
     */
    public static <Param> BooleanExpression genExpression(Class entityClass, Param param){
        try {

            ExQuery.BooleanExpressionBuilder builder = ExQuery.booleanExpressionBuilder();

            //获取QueryDsl生成的数据库操作类
            String queryDslClassName = entityClass.getName().replace(entityClass.getSimpleName(),
                    "Q" + entityClass.getSimpleName());
            Class queryDslClass = Class.forName(queryDslClassName);

            EntityPathBase queryDsl = (EntityPathBase) queryDslClass
                    .getDeclaredField(entityClass.getSimpleName().substring(0, 1).toLowerCase() +
                            entityClass.getSimpleName().substring(1))
                    .get(null);

            Field[] declaredFields = param.getClass().getDeclaredFields();
            Map<String, Object> betweenMap = Maps.newHashMap();
            for (Field declaredField : declaredFields) {
                declaredField.setAccessible(true);
                ParamQuery paramQuery = declaredField.getAnnotation(ParamQuery.class);
                if (paramQuery == null) {
                    continue;
                }
                QueryExpression expression = paramQuery.expression();
                String betweenKey = paramQuery.betweenKey();

                switch (expression){
                    case betweenDateStart:
                        //如果是开始结束时间类型
                        if(Strings.isNullOrEmpty(betweenKey)){
                            throw new HandleException("请输入正确的ParamQuery key");
                        }
                        if(betweenMap.containsKey(betweenKey)){
                            //已经存在结束时间
                            Object start = declaredField.get(param);
                            Object end = betweenMap.get(betweenKey);

                            DateTimePath dateTimePath = (DateTimePath) queryDslClass.getDeclaredField(betweenKey).get(queryDsl);
                            builder.and(ExQuery.betweenDate(dateTimePath, (Comparable) start,(Comparable) end));
                        } else {
                            betweenMap.put(betweenKey, declaredField.get(param));
                        }

                        break;
                    case betweenDateEnd:
                        //如果是开始结束时间类型
                        if(Strings.isNullOrEmpty(betweenKey)){
                            throw new HandleException("请输入正确的ParamQuery key");
                        }

                        if(betweenMap.containsKey(betweenKey)){
                            //已经存在开始时间
                            Object start = betweenMap.get(betweenKey);
                            Object end = declaredField.get(param);
                            DateTimePath dateTimePath = (DateTimePath) queryDslClass.getDeclaredField(betweenKey).get(queryDsl);
                            builder.and(ExQuery.betweenDate(dateTimePath, (Comparable) start,(Comparable) end));
                        } else {
                            betweenMap.put(betweenKey, declaredField.get(param));
                        }

                        break;
                    case betweenDate:
                        Object dateFiled = declaredField.get(param);
                        if (dateFiled != null) {
                            String[] dateBetween = dateFiled.toString().split(",");
                            LocalDateTime startDate = DateTimeUtils.stringToDateTime(dateBetween[0]);
                            LocalDateTime endDate = DateTimeUtils.stringToDateTime(dateBetween[1]);
                            DateTimePath dateTimePath = (DateTimePath) queryDslClass.getDeclaredField(betweenKey).get(queryDsl);
                            builder.and(ExQuery.betweenDate(dateTimePath, (Comparable) startDate,(Comparable) endDate));
                        }

                        break;
                    default:
                        //通过反射调用对应的ExQuery方法
                        builder.and( (BooleanExpression)
                                getExQueryMethod(expression.getMethod()).invoke(null,
                                        queryDslClass.getDeclaredField(declaredField.getName()).get(queryDsl),
                                        declaredField.get(param))
                        );

                }
            }
            return builder.build();
        } catch (Throwable e) {
            e.printStackTrace();
        }

        throw new HandleException("分页查询失败");
    }

    public static PageRequest toPageRequest(PageParam pageParam, Sort sort){
        if(sort == null){
            return PageRequest.of(pageParam.getPageIndex(), pageParam.getPageSize());
        } else {
            return PageRequest.of(pageParam.getPageIndex(), pageParam.getPageSize(), sort);
        }
    }

    public static PageRequest toPageRequest(PageParam pageParam){
        return toPageRequest(pageParam, null);
    }

    public static PageRequest toDefaultPageRequest(PageParam pageParam){
        return toPageRequest(pageParam, defaultPageSort());
    }

    /**
     * 获取分页排序
     * @param param
     * @return
     */
    private static <Param> Sort getPageSort(Param param){
        if(param == null){
            return defaultPageSort();
        }
        QuerySort sort = param.getClass().getAnnotation(QuerySort.class);
        if(sort == null){
            return defaultPageSort();
        }
        Sort.Direction direction = sort.direction();
        String[] properties = sort.properties();
        return new Sort(direction, properties);
    }



    /**
     * 反射获取ExQuery所有方法
     * @see ExQuery
     * @param methodName
     * @return
     */
    private static Method getExQueryMethod(String methodName){
        if(exQueryMethod == null){
            exQueryMethod = Maps.newHashMap();
            for (Method declaredMethod : ExQuery.class.getDeclaredMethods()) {
                exQueryMethod.put(declaredMethod.getName(), declaredMethod);
            }
        }

        Method method = exQueryMethod.get(methodName);
        if(method == null) {
            throw new HandleException("不存在方法" + methodName);
        }

        return method;
    }
}
