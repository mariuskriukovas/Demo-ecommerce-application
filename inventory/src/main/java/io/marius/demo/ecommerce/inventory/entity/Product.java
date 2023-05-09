package io.marius.demo.ecommerce.inventory.entity;

import io.marius.demo.ecommerce.persistence.entity.BaseEntity;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "product")
public class Product extends BaseEntity {
  @Column(name = "name", length = 100)
  private String name;

  @Column(name = "price")
  private Double price;

  @Column(name = "description", length = 2000)
  private String description;

  @ManyToOne
  @JoinColumn(name = "category_id", nullable = false)
  private ProductCategory productCategory;

  @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<ProductProperty> properties;

  @OneToMany(
      mappedBy = "product",
      cascade = CascadeType.ALL) // File service is responsible for removal
  private List<ProductFile> productFiles;

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

  public List<ProductFile> getProductFiles() {
    return productFiles;
  }

  public void setProductFiles(List<ProductFile> productFiles) {
    this.productFiles = productFiles;
  }
}
