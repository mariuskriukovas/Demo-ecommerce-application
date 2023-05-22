package io.marius.demo.ecommerce.inventory.repository;

import io.marius.demo.ecommerce.inventory.entity.ProductFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductFileRepository extends JpaRepository<ProductFile, Long> {}
