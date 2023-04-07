package io.marius.demo.ecommerce.inventory.repository;

import io.marius.demo.ecommerce.inventory.entity.InventoryItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryItemRepository extends JpaRepository<InventoryItem, Long> {
}
