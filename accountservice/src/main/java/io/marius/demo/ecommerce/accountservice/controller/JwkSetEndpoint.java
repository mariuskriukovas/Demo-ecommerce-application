package io.marius.demo.ecommerce.accountservice.controller;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
class JwkSetEndpoint {
  @Value("${jwt.public.key}")
  private RSAPublicKey rsaPublicKey;

  @GetMapping("/.well-known/jwks.json")
  @ResponseBody
  public Map<String, Object> getKey() {
    RSAKey key = new RSAKey.Builder(this.rsaPublicKey).build();
    return new JWKSet(key).toJSONObject();
  }
}
