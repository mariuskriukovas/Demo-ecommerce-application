package io.marius.demo.ecommerce.persistence.repository;

import io.marius.demo.ecommerce.persistence.entity.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BaseJpaRepository<T extends BaseEntity> extends JpaRepository<T, Long> {
    Optional<T> findByUid(String uid);

    void deleteByUid(String uid);
}
