package io.marius.demo.ecommerce.accountservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "shop_user")
@SuperBuilder
@NoArgsConstructor
@Getter
@Setter
public class ShopUser {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "username", length = 100)
  private String username;

  @Column(name = "password", length = 100)
  private String password;

  @Column(name = "email", length = 100)
  private String email;
}
