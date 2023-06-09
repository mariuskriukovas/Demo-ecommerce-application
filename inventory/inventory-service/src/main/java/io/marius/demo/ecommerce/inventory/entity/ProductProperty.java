package io.marius.demo.ecommerce.inventory.entity;

import io.marius.demo.ecommerce.common.persistence.entity.BaseEntity;
import io.marius.demo.ecommerce.inventory.api.entity.BaseProductProperty;
import jakarta.persistence.*;

@Entity
@Table(name = "product_property")
public class ProductProperty extends BaseEntity implements BaseProductProperty {
  @Column(name = "name", length = 100)
  private String name;

  @Column(name = "description", length = 1000)
  private String description;

  @ManyToOne
  @JoinColumn(name = "product_id", nullable = false)
  private Product product;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Product getProduct() {
    return product;
  }

  public void setProduct(Product product) {
    this.product = product;
  }
}
