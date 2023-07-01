package io.marius.demo.ecommerce.gatewayservice.util;

import java.util.Date;
import java.util.Objects;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {
  private final JwtDecoder jwtDecoder;

  public JwtUtil(JwtDecoder jwtDecoder) {
    this.jwtDecoder = jwtDecoder;
  }

  private boolean isTokenExpired(Jwt token) {
    return Objects.requireNonNull(token.getExpiresAt()).isBefore(new Date().toInstant());
  }

  public boolean isInvalid(String header) {
    return this.isTokenExpired(decodeBearerHeader(header));
  }

  private Jwt decodeBearerHeader(String header) {
    return jwtDecoder.decode(header.replace("Bearer ", ""));
  }
}
