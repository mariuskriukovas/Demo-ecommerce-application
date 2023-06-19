package io.marius.demo.ecommerce.inventory.api.entity;

import io.marius.demo.ecommerce.common.api.BaseIdentifiable;
import java.util.List;

public interface BaseProduct extends BaseIdentifiable {
  String getName();

  Double getPrice();

  String getDescription();

  BaseProductCategory getProductCategory();

  List<? extends BaseProductProperty> getProperties();

  List<? extends BaseProductFile> getProductFiles();
}
