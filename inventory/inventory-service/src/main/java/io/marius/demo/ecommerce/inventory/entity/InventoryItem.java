package io.marius.demo.ecommerce.inventory.entity;

import io.marius.demo.ecommerce.common.persistence.entity.BaseEntity;
import io.marius.demo.ecommerce.inventory.api.entity.BaseInventoryItem;
import jakarta.persistence.*;

@Entity
@Table(name = "inventory_item")
public class InventoryItem extends BaseEntity implements BaseInventoryItem {
  @Column(name = "quantity")
  private Integer quantity;

  @ManyToOne
  @JoinColumn(name = "product_id", nullable = false)
  private Product product;

  public Integer getQuantity() {
    return quantity;
  }

  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }

  public Product getProduct() {
    return product;
  }

  public void setProduct(Product product) {
    this.product = product;
  }
}
