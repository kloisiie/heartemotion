package com.brframework.commondb.core;

import com.brframework.common.utils.DateTimeUtils;
import com.brframework.commondb.annotation.QuerySort;
import com.brframework.commondb.annotation.param.ParamQuery;
import com.brframework.commondb.annotation.param.QueryExpression;
import com.brframework.commondb.utils.QueryUtils;
import com.brframework.commonweb.exception.HandleException;
import com.brframework.commonweb.json.PageParam;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.EntityPathBase;
import org.springframework.aop.framework.AopContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.io.Serializable;
import java.lang.reflect.*;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

/**
 * 通用Service的抽象实现
 * @author xu
 * @date 2019/1/9 10:35
 */
public abstract class AbstractEntityService<Entity, Id extends Serializable, Param> implements EntityService<Entity, Id, Param> {
    /**
     * 默认情况分页的排序字段
     */
    public static final String DEFAULT_PAGE_SORT_FIELD = "createDate";

    /**
     * 获取Repository对象
     * @return
     */
    public abstract CommonRepository<Id, Entity> getRepository();

    /**
     * 默认情况下的分页排序
     * @return
     */
    protected Sort defaultPageSort(){
        return new Sort(Sort.Direction.DESC, DEFAULT_PAGE_SORT_FIELD);
    }

    @Override
    public Page<Entity> page(PageParam page, Param param) {

        //通过反射获取需要使用到的QueryDsl参数
        Type genType = this.getClass().getGenericSuperclass();
        Class entityType = null;
        if(ParameterizedType.class.isInstance(genType)){
            //无法获取到User类，或者可能获取到错误的类型，如果有同名且不带包名的泛型存在
            ParameterizedType parameterizedType = (ParameterizedType) genType;
            entityType = (Class) parameterizedType.getActualTypeArguments()[0];
        }

        return QueryUtils.page(getRepository(), entityType, page, param);
    }

    @Override
    public Entity accessObject(Id id) {
        //需要触发findById的代理
        Optional<Entity> e = findById(id);
        if(! e.isPresent()){
            throw new HandleException("查不到" + id + "的相关数据");
        }
        return e.get();
    }

    @Override
    public Optional<Entity> findById(Id id) {
        return getRepository().findById(id);
    }

    @Override
    public void removeById(Id id) {
        getRepository().deleteById(id);
    }

    @Override
    public Entity save(Entity entity) {
        return getRepository().save(entity);
    }


    /**
     * 获取 目标对象
     * 记得设置exposeProxy=true
     * @return
     * @throws Exception
     */
    public AbstractEntityService<Entity, Id, Param> getTargetProxy() {

        try{
            Object target = AopContext.currentProxy();
            if(target instanceof AbstractEntityService){
                return (AbstractEntityService) target;
            } else {
                return this;
            }
        } catch (IllegalStateException e){
            return this;
        }
    }


}
