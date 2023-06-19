package io.marius.demo.ecommerce.account.api.resource;

import io.marius.demo.ecommerce.account.api.payload.LoginPayload;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface SessionResource {

  @PostMapping("authorize")
  ResponseEntity<Void> authorize(@Validated @RequestBody LoginPayload payload);
}
