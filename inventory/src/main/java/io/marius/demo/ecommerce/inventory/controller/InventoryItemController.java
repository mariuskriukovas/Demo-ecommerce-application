package io.marius.demo.ecommerce.inventory.controller;

import io.marius.demo.ecommerce.inventory.model.view.InventoryItemView;
import io.marius.demo.ecommerce.inventory.service.InventoryItemService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("inventory-items")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InventoryItemController {
    InventoryItemService inventoryItemService;
    @GetMapping
    public Page<InventoryItemView> getInventoryItems() {
        return inventoryItemService.getAllInventoryItems();
    }
}
