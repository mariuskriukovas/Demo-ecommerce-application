package io.marius.demo.ecommerce.inventory.api.entity;

import io.marius.demo.ecommerce.common.api.BaseIdentifiable;

public interface BaseInventoryItem extends BaseIdentifiable {
  Integer getQuantity();

  BaseProduct getProduct();
}
