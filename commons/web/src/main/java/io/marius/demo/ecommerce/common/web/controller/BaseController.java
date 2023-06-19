package io.marius.demo.ecommerce.common.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

public abstract class BaseController {
  @GetMapping("/health")
  ResponseEntity<String> getHealth() {
    return ResponseEntity.ok().body("OK");
  }
}
