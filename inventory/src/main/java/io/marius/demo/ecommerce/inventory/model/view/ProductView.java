package io.marius.demo.ecommerce.inventory.model.view;

import io.marius.demo.ecommerce.persistence.api.model.view.ClassifierView;
import java.util.List;

public class ProductView {
  private Long id;
  private String name;
  private Double price;
  private String description;
  private ClassifierView productCategory;
  private List<ClassifierView> properties;
  private List<FileView> productFiles;

  public List<ClassifierView> getProperties() {
    return properties;
  }

  public void setProperties(List<ClassifierView> properties) {
    this.properties = properties;
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

  public ClassifierView getProductCategory() {
    return productCategory;
  }

  public void setProductCategory(ClassifierView productCategory) {
    this.productCategory = productCategory;
  }
}
