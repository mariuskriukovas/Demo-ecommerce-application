package io.marius.demo.ecommerce.inventory.entity;

import io.marius.demo.ecommerce.common.persistence.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "product_category")
public class ProductCategory extends BaseEntity {
  @Column(name = "name", length = 100)
  private String name;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
