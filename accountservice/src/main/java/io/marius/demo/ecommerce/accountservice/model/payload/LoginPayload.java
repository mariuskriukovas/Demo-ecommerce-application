package io.marius.demo.ecommerce.accountservice.model.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class LoginPayload {
  @NotBlank(message = "Invalid username: Empty username")
  @NotNull(message = "Invalid username: Username is NULL")
  private String username;

  @NotBlank(message = "Invalid password: Empty password")
  @NotNull(message = "Invalid password: Password is NULL")
  private String password;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
