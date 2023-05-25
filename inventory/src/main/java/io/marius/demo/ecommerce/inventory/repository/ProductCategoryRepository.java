package io.marius.demo.ecommerce.inventory.repository;

import io.marius.demo.ecommerce.inventory.entity.ProductCategory;
import io.marius.demo.ecommerce.persistence.repository.BaseJpaRepository;
import java.util.Optional;

public interface ProductCategoryRepository extends BaseJpaRepository<ProductCategory> {
  Optional<ProductCategory> findByName(String name);
}
