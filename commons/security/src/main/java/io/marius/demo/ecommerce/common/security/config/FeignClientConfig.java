package io.marius.demo.ecommerce.common.security.config;

import feign.RequestInterceptor;
import io.marius.demo.ecommerce.account.api.client.SessionClient;
import io.marius.demo.ecommerce.account.api.payload.LoginPayload;
import java.util.Date;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;

@Configuration
@EnableFeignClients(basePackageClasses = {SessionClient.class})
public class FeignClientConfig {
  private final SessionClient sessionClient;
  private final JwtDecoder jwtDecoder;

  @Value("${services.account.username}")
  private String username;

  @Value("${services.account.password}")
  private String password;

  private String authorizationToken;

  public FeignClientConfig(SessionClient sessionClient, JwtDecoder jwtDecoder) {
    this.sessionClient = sessionClient;
    this.jwtDecoder = jwtDecoder;
  }

  @Bean
  public RequestInterceptor requestInterceptor() {
    return requestTemplate -> {
      if (!(requestTemplate.feignTarget() instanceof SessionClient)) {
        requestTemplate.header(HttpHeaders.AUTHORIZATION, getAuthorizationHeader());
      }
    };
  }

  private String getAuthorizationToken() {
    LoginPayload loginPayload = new LoginPayload();

    loginPayload.setUsername(username);
    loginPayload.setPassword(password);

    // fixme comment
    System.err.println("JWT Retracted at: " + new Date());
    ResponseEntity<Void> request = sessionClient.authorize(loginPayload);

    String header = request.getHeaders().get(HttpHeaders.AUTHORIZATION).toString();
    return header.substring(7, header.length() - 1);
  }

  private String getAuthorizationHeader() {
    Jwt token = authorizationToken != null ? jwtDecoder.decode(authorizationToken) : null;
    if (token == null || isTokenExpired(token)) {
      this.authorizationToken = getAuthorizationToken();
    }

    return "Bearer" + authorizationToken;
  }

  private boolean isTokenExpired(Jwt token) {
    return Objects.requireNonNull(token.getExpiresAt()).isBefore(new Date().toInstant());
  }
}
