package com.brframework.commondb.core;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * Created by xu
 *
 * 通用的Repository
 *
 * @author xu
 * @date 18-9-14 上午10:58
 */
@NoRepositoryBean
public interface CommonRepository<K extends Serializable, E> extends PagingAndSortingRepository<E, K>,
        JpaSpecificationExecutor<E>,
        QuerydslPredicateExecutor<E> {
}
