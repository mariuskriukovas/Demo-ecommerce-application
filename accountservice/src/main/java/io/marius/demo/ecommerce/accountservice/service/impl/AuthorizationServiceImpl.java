package io.marius.demo.ecommerce.accountservice.service.impl;

import io.marius.demo.ecommerce.accountservice.model.payload.LoginPayload;
import io.marius.demo.ecommerce.accountservice.repository.ShopUserRepository;
import io.marius.demo.ecommerce.accountservice.service.AuthorizationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("AuthorizationService")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthorizationServiceImpl implements AuthorizationService {
  ShopUserRepository userRepository;

  @Override
  @Transactional(readOnly = true)
  public String login(LoginPayload payload) {
    userRepository
        .findByUsernameAndPassword(payload.getUsername(), payload.getPassword())
        .orElseThrow(() -> new RuntimeException("Wrong user credentials"));

    return "Autentificated";
  }
}
