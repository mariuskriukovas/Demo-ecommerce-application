package io.marius.demo.ecommerce.accountservice.entity;

import io.marius.demo.ecommerce.accountservice.security.enums.UserRole;
import io.marius.demo.ecommerce.persistence.entity.BaseEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "role")
public class Role extends BaseEntity {
  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private ShopUser shopUser;

  @Column(name = "role", length = 100, nullable = false)
  @Convert(converter = UserRole.Converter.class)
  private UserRole role;

  public ShopUser getShopUser() {
    return shopUser;
  }

  public void setShopUser(ShopUser shopUser) {
    this.shopUser = shopUser;
  }

  public UserRole getRole() {
    return role;
  }

  public void setRole(UserRole role) {
    this.role = role;
  }
}
