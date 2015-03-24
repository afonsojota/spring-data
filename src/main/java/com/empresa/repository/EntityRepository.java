package com.empresa.repository;

import com.empresa.entity.AbstractEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * Created by root on 3/24/15.
 */
@NoRepositoryBean
public interface EntityRepository<T extends AbstractEntity> extends JpaRepository<T, Long>, JpaSpecificationExecutor, QueryDslPredicateExecutor<T> {
    // nothing yet
}