package io.marius.demo.ecommerce.inventory.api.entity;

import io.marius.demo.ecommerce.common.api.BaseIdentifiable;

public interface BaseProductFile extends BaseIdentifiable {
  BaseFileMetadata getFileMetadata();
}
