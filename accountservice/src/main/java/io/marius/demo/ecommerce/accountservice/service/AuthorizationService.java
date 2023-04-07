package io.marius.demo.ecommerce.accountservice.service;

import io.marius.demo.ecommerce.accountservice.model.payload.LoginPayload;

public interface AuthorizationService {
  String login(LoginPayload payload);
}
