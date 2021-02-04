package com.brframework.commonwebbase.dao;

import com.brframework.commondb.core.CommonRepository;
import com.brframework.commonwebbase.entity.Dictionary;
import org.springframework.stereotype.Repository;

/**
 * Created by xu
 *
 * 字典
 *
 * @author xu
 * @date 18-4-19 上午9:51
 */
@Repository
public interface DictionaryDao extends CommonRepository<Integer, Dictionary> {

    /**
     * 通过Key查询
     * @param key
     * @return
     */
    Dictionary findByKey(String key);

    /**
     * 通过Key删除
     * @param key
     */
    void deleteByKey(String key);
}
