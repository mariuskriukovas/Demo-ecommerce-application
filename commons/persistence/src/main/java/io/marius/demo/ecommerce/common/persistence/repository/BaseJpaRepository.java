package io.marius.demo.ecommerce.common.persistence.repository;

import io.marius.demo.ecommerce.common.persistence.entity.BaseEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BaseJpaRepository<T extends BaseEntity> extends JpaRepository<T, Long> {
  Optional<T> findByUid(String uid);

  void deleteByUid(String uid);
}
