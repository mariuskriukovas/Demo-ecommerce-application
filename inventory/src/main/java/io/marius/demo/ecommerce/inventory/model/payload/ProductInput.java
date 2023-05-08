package io.marius.demo.ecommerce.inventory.model.payload;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public class ProductInput {
  Long id;
  String name;
  Double price;
  String description;
  String productCategory;
  List<PropertyInput> properties;
  List<MultipartFile> files;

  public List<MultipartFile> getFiles() {
    return files;
  }

  public void setFiles(List<MultipartFile> files) {
    this.files = files;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getProductCategory() {
    return productCategory;
  }

  public void setProductCategory(String productCategory) {
    this.productCategory = productCategory;
  }

  public List<PropertyInput> getProperties() {
    return properties;
  }

  public void setProperties(List<PropertyInput> properties) {
    this.properties = properties;
  }
}
