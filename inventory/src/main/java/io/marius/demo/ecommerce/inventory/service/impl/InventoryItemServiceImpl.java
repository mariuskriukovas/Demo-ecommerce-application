package io.marius.demo.ecommerce.inventory.service.impl;

import io.marius.demo.ecommerce.inventory.entity.InventoryItem;
import io.marius.demo.ecommerce.inventory.model.view.InventoryItemView;
import io.marius.demo.ecommerce.inventory.repository.InventoryItemRepository;
import io.marius.demo.ecommerce.inventory.service.InventoryItemService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service("InventoryItemService")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InventoryItemServiceImpl implements InventoryItemService {
    InventoryItemRepository inventoryItemRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<InventoryItemView> getAllInventoryItems() {
        return new PageImpl<>(
                inventoryItemRepository.findAll().stream()
                        .map(this::toInventoryItemView)
                        .collect(Collectors.toList()));
    }

    private InventoryItemView toInventoryItemView(InventoryItem item) {
        return InventoryItemView.builder()
                .id(item.getId())
                .name(item.getName())
                .quantity(item.getQuantity())
                .price(item.getPrice())
                .build();
    }
}
