package io.marius.demo.ecommerce.inventory.service;

import io.marius.demo.ecommerce.inventory.model.view.InventoryItemView;
import org.springframework.data.domain.Page;


public interface InventoryItemService {
    Page<InventoryItemView> getAllInventoryItems();
}
