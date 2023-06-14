package io.marius.demo.ecommerce.elasticindexerservice.elastic.document;

import io.marius.demo.ecommerce.inventory.api.entity.BaseFileMetadata;

public record FileMetadata(String fileName, String s3Url, String extension, String fileKey)
    implements BaseFileMetadata {

  @Override
  public String getFileName() {
    return fileName;
  }

  @Override
  public String getS3Url() {
    return s3Url;
  }

  @Override
  public String getExtension() {
    return extension;
  }

  @Override
  public String getFileKey() {
    return fileKey;
  }
}
