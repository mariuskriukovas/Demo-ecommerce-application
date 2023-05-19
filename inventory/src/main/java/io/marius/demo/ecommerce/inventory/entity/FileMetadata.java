package io.marius.demo.ecommerce.inventory.entity;

import io.marius.demo.ecommerce.persistence.entity.BaseEntity;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "file_metadata")
public class FileMetadata extends BaseEntity {
  @Column(name = "file_name", length = 500)
  private String fileName;

  @Column(name = "file_key", length = 100)
  private String fileKey;

  @Column(name = "file_extension", length = 10)
  private String extension;

  @Column(name = "s3_url", length = 500)
  private String s3Url;

  @OneToMany(mappedBy = "fileMetadata", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<ProductFile> productFiles;

  public List<ProductFile> getProductFiles() {
    return productFiles;
  }

  public void setProductFiles(List<ProductFile> productFiles) {
    this.productFiles = productFiles;
  }

  public String getFileName() {
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  public String getS3Url() {
    return s3Url;
  }

  public void setS3Url(String s3Url) {
    this.s3Url = s3Url;
  }

  public String getExtension() {
    return extension;
  }

  public void setExtension(String extension) {
    this.extension = extension;
  }

  public String getFileKey() {
    return fileKey;
  }

  public void setFileKey(String fileKey) {
    this.fileKey = fileKey;
  }

  public static final class FileMetadataBuilder {
    private String fileName;
    private String fileKey;
    private String extension;
    private String s3Url;
    private List<ProductFile> productFiles;
    private Long id;

    private FileMetadataBuilder() {}

    public static FileMetadataBuilder aFileMetadata() {
      return new FileMetadataBuilder();
    }

    public FileMetadataBuilder withFileName(String fileName) {
      this.fileName = fileName;
      return this;
    }

    public FileMetadataBuilder withFileKey(String fileKey) {
      this.fileKey = fileKey;
      return this;
    }

    public FileMetadataBuilder withExtension(String extension) {
      this.extension = extension;
      return this;
    }

    public FileMetadataBuilder withS3Url(String s3Url) {
      this.s3Url = s3Url;
      return this;
    }

    public FileMetadataBuilder withProductFiles(List<ProductFile> productFiles) {
      this.productFiles = productFiles;
      return this;
    }

    public FileMetadataBuilder withId(Long id) {
      this.id = id;
      return this;
    }

    public FileMetadata build() {
      FileMetadata fileMetadata = new FileMetadata();
      fileMetadata.setFileName(fileName);
      fileMetadata.setFileKey(fileKey);
      fileMetadata.setExtension(extension);
      fileMetadata.setS3Url(s3Url);
      fileMetadata.setProductFiles(productFiles);
      fileMetadata.setId(id);
      return fileMetadata;
    }
  }
}
