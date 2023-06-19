package io.marius.demo.ecommerce.inventory.api.entity;

import io.marius.demo.ecommerce.common.api.BaseIdentifiable;

public interface BaseFileMetadata extends BaseIdentifiable {
  String getFileName();

  String getS3Url();

  String getExtension();

  String getFileKey();
}
