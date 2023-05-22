package io.marius.demo.ecommerce.inventory.repository;

import io.marius.demo.ecommerce.inventory.entity.FileMetadata;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<FileMetadata, Long> {}
