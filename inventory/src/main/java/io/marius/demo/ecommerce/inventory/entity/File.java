package io.marius.demo.ecommerce.inventory.entity;

import io.marius.demo.ecommerce.persistence.entity.BaseEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "file")
public class File extends BaseEntity {
  @Column(name = "file_name", length = 500)
  private String fileName;

  @Column(name = "s3_etag", length = 100)
  private String s3Etag;

  @Column(name = "s3_url", length = 500)
  private String s3Url;

  public String getFileName() {
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  public String getS3Etag() {
    return s3Etag;
  }

  public void setS3Etag(String s3Etag) {
    this.s3Etag = s3Etag;
  }

  public String getS3Url() {
    return s3Url;
  }

  public void setS3Url(String s3Url) {
    this.s3Url = s3Url;
  }

  public static final class FileBuilder {
    private String fileName;
    private String s3Etag;
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

    public FileBuilder withS3Etag(String s3Etag) {
      this.s3Etag = s3Etag;
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
      file.setS3Etag(s3Etag);
      file.setS3Url(s3Url);
      file.setId(id);
      return file;
    }
  }
}
