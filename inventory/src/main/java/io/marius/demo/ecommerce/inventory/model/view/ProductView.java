package io.marius.demo.ecommerce.inventory.model.view;

import io.marius.demo.ecommerce.inventory.entity.ProductCategory;
import io.marius.demo.ecommerce.inventory.entity.ProductProperty;
import java.util.List;

public class ProductView {
  private Long id;
  private String name;
  private Double price;
  private String description;
  private ProductCategory productCategory;
  private List<ProductProperty> properties;
  private List<FileView> productFiles;

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

  public ProductCategory getProductCategory() {
    return productCategory;
  }

  public void setProductCategory(ProductCategory productCategory) {
    this.productCategory = productCategory;
  }

  public List<ProductProperty> getProperties() {
    return properties;
  }

  public void setProperties(List<ProductProperty> properties) {
    this.properties = properties;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public List<FileView> getProductFiles() {
    return productFiles;
  }

  public void setProductFiles(List<FileView> productFiles) {
    this.productFiles = productFiles;
  }
}
