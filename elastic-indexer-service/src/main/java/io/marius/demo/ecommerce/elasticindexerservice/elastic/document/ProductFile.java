package io.marius.demo.ecommerce.elasticindexerservice.elastic.document;

import io.marius.demo.ecommerce.inventory.api.entity.BaseFileMetadata;
import io.marius.demo.ecommerce.inventory.api.entity.BaseProductFile;

public record ProductFile(String uid, BaseFileMetadata fileMetadata) implements BaseProductFile {
  @Override
  public BaseFileMetadata getFileMetadata() {
    return fileMetadata;
  }

  @Override
  public String getUid() {
    return uid;
  }
}
