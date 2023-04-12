package io.marius.demo.ecommerce.accountservice.controller;

import io.marius.demo.ecommerce.accountservice.model.payload.LoginPayload;
import io.marius.demo.ecommerce.accountservice.service.AuthorizationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("sessions")
public class SessionController {
  private final AuthorizationService authorizationService;

  public SessionController(AuthorizationService authorizationService) {
    this.authorizationService = authorizationService;
  }

  @PostMapping("login")
  ResponseEntity<String> login(@RequestBody LoginPayload payload) {
    return authorizationService.login(payload);
  }

  @GetMapping("test")
  String test() {
    return "OK";
  }
}
