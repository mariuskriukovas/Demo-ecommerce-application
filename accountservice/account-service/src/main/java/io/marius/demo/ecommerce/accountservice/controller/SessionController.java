package io.marius.demo.ecommerce.accountservice.controller;

import io.marius.demo.ecommerce.account.api.payload.LoginPayload;
import io.marius.demo.ecommerce.account.api.resource.SessionResource;
import io.marius.demo.ecommerce.accountservice.model.view.UserView;
import io.marius.demo.ecommerce.accountservice.service.AuthorizationService;
import io.marius.demo.ecommerce.common.web.controller.BaseController;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("sessions")
public class SessionController extends BaseController implements SessionResource {
  private final AuthorizationService authorizationService;

  public SessionController(AuthorizationService authorizationService) {
    this.authorizationService = authorizationService;
  }

  @PostMapping("login")
  public ResponseEntity<UserView> login(@Validated @RequestBody LoginPayload payload) {
    return authorizationService.login(payload);
  }

  @PostMapping("authorize")
  public ResponseEntity<Void> authorize(@Validated @RequestBody LoginPayload payload) {
    return authorizationService.authorize(payload);
  }

  @GetMapping("sorry")
  public ResponseEntity<String> login() {
    return ResponseEntity.ok("Sorry, you need to be authenticated first :(");
  }
}
