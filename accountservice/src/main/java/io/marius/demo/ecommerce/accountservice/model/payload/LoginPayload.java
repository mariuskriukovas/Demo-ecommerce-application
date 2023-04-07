package io.marius.demo.ecommerce.accountservice.model.payload;

import lombok.Data;

@Data
public class LoginPayload {
  String username;
  String password;
}
