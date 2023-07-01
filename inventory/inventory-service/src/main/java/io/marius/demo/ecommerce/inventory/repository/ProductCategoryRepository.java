package io.marius.demo.ecommerce.inventory.repository;

import io.marius.demo.ecommerce.common.persistence.repository.BaseJpaRepository;
import io.marius.demo.ecommerce.inventory.entity.ProductCategory;
import java.util.Optional;

public interface ProductCategoryRepository extends BaseJpaRepository<ProductCategory> {
  Optional<ProductCategory> findByName(String name);
}
