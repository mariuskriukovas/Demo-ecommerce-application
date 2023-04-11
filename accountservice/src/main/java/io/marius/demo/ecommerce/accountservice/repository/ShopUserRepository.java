package io.marius.demo.ecommerce.accountservice.repository;

import io.marius.demo.ecommerce.accountservice.entity.ShopUser;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopUserRepository extends JpaRepository<ShopUser, Long> {
  Optional<ShopUser> findByUsername(String username);

  Optional<ShopUser> findByUsernameAndPassword(String username, String password);
}
