package io.marius.demo.ecommerce.inventory.repository;

import io.marius.demo.ecommerce.common.persistence.repository.BaseJpaRepository;
import io.marius.demo.ecommerce.inventory.entity.ProductProperty;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface ProductPropertyRepository
    extends BaseJpaRepository<ProductProperty>, QuerydslPredicateExecutor<ProductProperty> {}
