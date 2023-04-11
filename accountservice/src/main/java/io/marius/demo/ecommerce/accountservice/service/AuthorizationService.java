package io.marius.demo.ecommerce.accountservice.service;

import io.marius.demo.ecommerce.accountservice.model.payload.LoginPayload;
import org.springframework.http.ResponseEntity;

public interface AuthorizationService {
  ResponseEntity<String> login(LoginPayload payload);
}
