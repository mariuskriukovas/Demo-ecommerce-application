package io.marius.demo.ecommerce.inventory.repository;

import io.marius.demo.ecommerce.inventory.entity.ProductProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface PropertyRepository
    extends JpaRepository<ProductProperty, Long>, QuerydslPredicateExecutor<ProductProperty> {}
