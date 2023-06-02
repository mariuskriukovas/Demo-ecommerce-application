package io.marius.demo.ecommerce.accountservice.controller;

import io.marius.demo.ecommerce.accountservice.model.payload.LoginPayload;
import io.marius.demo.ecommerce.accountservice.model.view.UserView;
import io.marius.demo.ecommerce.accountservice.service.AuthorizationService;
import io.marius.demo.ecommerce.common.api.controller.BaseController;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("sessions")
public class SessionController extends BaseController {
  private final AuthorizationService authorizationService;

  public SessionController(AuthorizationService authorizationService) {
    this.authorizationService = authorizationService;
  }

  @PostMapping("login")
  ResponseEntity<UserView> login(@Validated @RequestBody LoginPayload payload) {
    return authorizationService.login(payload);
  }

  @GetMapping("sorry")
  ResponseEntity<String> login() {
    return ResponseEntity.ok("Sorry, you need to be authenticated first :(");
  }
}
