package io.marius.demo.ecommerce.inventory.repository;

import io.marius.demo.ecommerce.inventory.entity.ProductProperty;
import io.marius.demo.ecommerce.persistence.repository.BaseJpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface ProductPropertyRepository
    extends BaseJpaRepository<ProductProperty>, QuerydslPredicateExecutor<ProductProperty> {}
