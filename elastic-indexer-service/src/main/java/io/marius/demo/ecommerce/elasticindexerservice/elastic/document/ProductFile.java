package io.marius.demo.ecommerce.elasticindexerservice.elastic.document;

import io.marius.demo.ecommerce.inventory.api.entity.BaseFileMetadata;
import io.marius.demo.ecommerce.inventory.api.entity.BaseProductFile;

public record ProductFile(BaseFileMetadata fileMetadata) implements BaseProductFile {
  @Override
  public BaseFileMetadata getFileMetadata() {
    return fileMetadata;
  }
}
