package io.marius.demo.ecommerce.inventory.model.view;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class InventoryItemView {
    Long id;
    String name;
    Double price;
    Integer quantity;
}
