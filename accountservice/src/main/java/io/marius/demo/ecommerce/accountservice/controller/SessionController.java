package io.marius.demo.ecommerce.accountservice.controller;

import io.marius.demo.ecommerce.accountservice.model.payload.LoginPayload;
import io.marius.demo.ecommerce.accountservice.service.AuthorizationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("sessions")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SessionController {
  AuthorizationService authorizationService;

  @PostMapping("login")
  String login(@RequestBody LoginPayload payload) {
    return authorizationService.login(payload);
  }

  //  Should be not accessible through SecurityFilterChain
  @GetMapping("test")
  String test() {
    return "OK";
  }
}
