package com.brframework.commondb.core;

import com.brframework.commonweb.json.PageParam;
import org.springframework.data.domain.Page;

import java.util.Optional;

/**
 * 通用Service
 * @author xu
 * @date 2019/1/7 11:52
 */
public interface EntityService<Entity, Id, Param> {


    /**
     * 分页获取送券信息
     * @param page
     * @param param
     * @return
     */
    Page<Entity> page(PageParam page, Param param);

    /**
     * 通过id获取，不存在时将抛出异常
     * @param id
     * @return
     */
    Entity accessObject(Id id);

    /**
     * 通过ID查询
     * @param id
     * @return
     */
    Optional<Entity> findById(Id id);

    /**
     * 通过ID删除
     * @param id
     */
    void removeById(Id id);

    /**
     * 保存
     * @return
     */
    Entity save(Entity entity);

}
