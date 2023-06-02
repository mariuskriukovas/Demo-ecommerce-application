package io.marius.demo.ecommerce.common.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

public abstract class BaseController {
  @GetMapping("/health")
  ResponseEntity<String> getHealth() {
    return ResponseEntity.ok().body("OK");
  }
}
