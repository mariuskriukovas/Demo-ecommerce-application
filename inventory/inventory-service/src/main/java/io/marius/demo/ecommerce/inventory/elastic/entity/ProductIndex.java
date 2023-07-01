package io.marius.demo.ecommerce.inventory.elastic.entity;

import io.marius.demo.ecommerce.common.api.view.ClassifierView;
import io.marius.demo.ecommerce.inventory.api.entity.*;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

@Document(indexName = "public_product_index")
public class ProductIndex implements BaseProduct {
  private List<InnerProductProperty> properties;
  private String name;
  private ClassifierView category;
  private Double price;
  private String description;
  private List<InnerProductFile> files;

  @Id
  @Field(name = "uid")
  private String uid;

  public ClassifierView getCategory() {
    return category;
  }

  public void setCategory(ClassifierView category) {
    this.category = category;
  }

  @Override
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  @Override
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @Override
  public BaseProductCategory getProductCategory() {
    return null;
  }

  @Override
  public List<? extends BaseProductProperty> getProperties() {
    return properties;
  }

  public void setProperties(List<InnerProductProperty> properties) {
    this.properties = properties;
  }

  @Override
  public List<? extends BaseProductFile> getProductFiles() {
    return files;
  }

  public String getUid() {
    return uid;
  }

  public void setUid(String uid) {
    this.uid = uid;
  }

  public List<InnerProductFile> getFiles() {
    return files;
  }

  public void setFiles(List<InnerProductFile> files) {
    this.files = files;
  }

  //  Todo not sure if it is the best way to go with inner classes

  public record InnerFileMetadata(
      String uid, String fileName, String s3Url, String extension, String fileKey)
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

    @Override
    public String getUid() {
      return uid;
    }
  }

  public record InnerProductFile(String uid, InnerFileMetadata fileMetadata)
      implements BaseProductFile {
    @Override
    public BaseFileMetadata getFileMetadata() {
      return fileMetadata;
    }

    @Override
    public String getUid() {
      return uid;
    }
  }

  public record InnerProductProperty(String uid, String name, String description)
      implements BaseProductProperty {
    @Override
    public String getName() {
      return name;
    }

    @Override
    public String getDescription() {
      return description;
    }

    @Override
    public String getUid() {
      return uid;
    }
  }
}
