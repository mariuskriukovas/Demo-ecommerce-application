package io.marius.demo.ecommerce.inventory.entity;

import io.marius.demo.ecommerce.persistence.entity.BaseEntity;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "file")
public class File extends BaseEntity {
  @Column(name = "file_name", length = 500)
  private String fileName;

  @Column(name = "key", length = 100)
  private String key;

  @Column(name = "s3_url", length = 500)
  private String s3Url;

  @OneToMany(mappedBy = "file", cascade = CascadeType.ALL, orphanRemoval = true)
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

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public static final class FileBuilder {
    private String fileName;
    private String key;
    private String s3Url;
    private Long id;

    private FileBuilder() {}

    public static FileBuilder aFile() {
      return new FileBuilder();
    }

    public FileBuilder withFileName(String fileName) {
      this.fileName = fileName;
      return this;
    }

    public FileBuilder withKey(String key) {
      this.key = key;
      return this;
    }

    public FileBuilder withS3Url(String s3Url) {
      this.s3Url = s3Url;
      return this;
    }

    public FileBuilder withId(Long id) {
      this.id = id;
      return this;
    }

    public File build() {
      File file = new File();
      file.setFileName(fileName);
      file.setKey(key);
      file.setS3Url(s3Url);
      file.setId(id);
      return file;
    }
  }
}
