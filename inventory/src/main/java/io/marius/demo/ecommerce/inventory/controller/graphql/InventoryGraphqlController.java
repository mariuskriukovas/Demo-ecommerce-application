package io.marius.demo.ecommerce.inventory.controller.graphql;

import io.marius.demo.ecommerce.inventory.entity.InventoryItem;
import io.marius.demo.ecommerce.inventory.model.query.InventoryItemsFilter;
import io.marius.demo.ecommerce.inventory.service.InventoryService;
import java.util.List;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
public class InventoryGraphqlController {
  private final InventoryService inventoryService;

  public InventoryGraphqlController(InventoryService inventoryService) {
    this.inventoryService = inventoryService;
  }

  @QueryMapping(value = "allInventoryItems")
  public List<InventoryItem> getAllInventoryItems(
      @Argument(name = "filter") InventoryItemsFilter filter) {
    return inventoryService.findAllInventoryItems(filter);
  }
}
