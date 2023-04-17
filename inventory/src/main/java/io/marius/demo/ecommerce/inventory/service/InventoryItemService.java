package io.marius.demo.ecommerce.inventory.service;

import io.marius.demo.ecommerce.inventory.entity.InventoryItem;
import io.marius.demo.ecommerce.inventory.model.view.InventoryItemView;
import io.marius.demo.ecommerce.inventory.repository.InventoryItemRepository;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("InventoryItemService")
public class InventoryItemService {
  private final InventoryItemRepository inventoryItemRepository;

  public InventoryItemService(InventoryItemRepository inventoryItemRepository) {
    this.inventoryItemRepository = inventoryItemRepository;
  }

  @Transactional(readOnly = true)
  public Page<InventoryItemView> getAllInventoryItems() {
    return new PageImpl<>(
        inventoryItemRepository.findAll().stream()
            .map(this::toInventoryItemView)
            .collect(Collectors.toList()));
  }

  private InventoryItemView toInventoryItemView(InventoryItem item) {
    return InventoryItemView.InventoryItemViewBuilder.anInventoryItemView()
        .id(item.getId())
        .name(item.getProduct().getName())
        .quantity(item.getQuantity())
        .price(item.getProduct().getPrice())
        .build();
  }
}
