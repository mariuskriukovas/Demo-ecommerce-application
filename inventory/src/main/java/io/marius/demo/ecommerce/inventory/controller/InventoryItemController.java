package io.marius.demo.ecommerce.inventory.controller;

import io.marius.demo.ecommerce.inventory.model.view.InventoryItemView;
import io.marius.demo.ecommerce.inventory.service.InventoryItemService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("inventory-items")
public class InventoryItemController {
    private final InventoryItemService inventoryItemService;

    public InventoryItemController(InventoryItemService inventoryItemService) {
        this.inventoryItemService = inventoryItemService;
    }

    @GetMapping
    public Page<InventoryItemView> getInventoryItems() {
        return inventoryItemService.getAllInventoryItems();
    }
}
