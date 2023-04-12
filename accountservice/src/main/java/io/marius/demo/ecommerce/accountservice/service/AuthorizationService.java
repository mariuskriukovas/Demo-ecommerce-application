package io.marius.demo.ecommerce.accountservice.service;

import static java.lang.String.format;
import static java.util.stream.Collectors.joining;

import io.marius.demo.ecommerce.accountservice.entity.ShopUser;
import io.marius.demo.ecommerce.accountservice.model.payload.LoginPayload;
import java.time.Instant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("AuthorizationService")
public class AuthorizationService {
  private final AuthenticationManager authenticationManager;
  private final JwtEncoder jwtEncoder;

  @Value("${jwt.claims.custom.issuer}")
  private String jwtIssuer;

  @Value("${jwt.claims.custom.expiry}")
  private String jwtExpiry;

  public AuthorizationService(AuthenticationManager authenticationManager, JwtEncoder jwtEncoder) {
    this.authenticationManager = authenticationManager;
    this.jwtEncoder = jwtEncoder;
  }

  @Transactional(readOnly = true)
  public ResponseEntity<String> login(LoginPayload payload) {
    try {
      Authentication authentication =
          authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(
                  payload.getUsername(), payload.getPassword()));

      return ResponseEntity.ok()
          .header(HttpHeaders.AUTHORIZATION, buildJwtToken(authentication))
          .body("Authorized");
    } catch (BadCredentialsException ex) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
  }

  private String buildJwtToken(Authentication authentication) {
    ShopUser user = (ShopUser) authentication.getPrincipal();

    Instant now = Instant.now();
    long expiry = Long.parseLong(jwtExpiry);

    String scope =
        authentication.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(joining(" "));

    JwtClaimsSet claims =
        JwtClaimsSet.builder()
            .issuer(jwtIssuer)
            .issuedAt(now)
            .expiresAt(now.plusSeconds(expiry))
            .subject(format("%s,%s", user.getId(), user.getUsername()))
            .claim("roles", scope)
            .build();

    return String.format(
        "%s %s",
        "Bearer", this.jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue());
  }
}
