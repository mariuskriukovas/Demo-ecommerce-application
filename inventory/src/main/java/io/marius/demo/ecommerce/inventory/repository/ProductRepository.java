package io.marius.demo.ecommerce.inventory.repository;

import io.marius.demo.ecommerce.inventory.entity.Product;
import io.marius.demo.ecommerce.persistence.repository.BaseJpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface ProductRepository
    extends BaseJpaRepository<Product>, QuerydslPredicateExecutor<Product> {}
