package io.marius.demo.ecommerce.inventory.repository;

import io.marius.demo.ecommerce.common.persistence.repository.BaseJpaRepository;
import io.marius.demo.ecommerce.inventory.entity.Product;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface ProductRepository
    extends BaseJpaRepository<Product>, QuerydslPredicateExecutor<Product> {}
