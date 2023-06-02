package io.marius.demo.ecommerce.gatewayservice.filter;

import io.marius.demo.ecommerce.gatewayservice.util.JwtUtil;
import io.marius.demo.ecommerce.gatewayservice.validator.RouterValidator;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@RefreshScope
@Component
public class AuthenticationFilter implements WebFilter, Ordered {
  private final RouterValidator routerValidator;

  private final JwtUtil jwtUtil;

  public AuthenticationFilter(RouterValidator routerValidator, JwtUtil jwtUtil) {
    this.routerValidator = routerValidator;
    this.jwtUtil = jwtUtil;
  }

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
    ServerHttpRequest request = exchange.getRequest();

    if (routerValidator.isSecured.test(request)) {
      if (this.isAuthMissing(request))
        return this.onError(
            exchange, "Authorization header is missing in request", HttpStatus.UNAUTHORIZED);

      final String authHeader = this.getAuthHeader(request);

      if (jwtUtil.isInvalid(authHeader))
        return this.onError(exchange, "Authorization header is invalid", HttpStatus.UNAUTHORIZED);
    }
    return chain.filter(exchange);
  }

  @Override
  public int getOrder() {
    return Ordered.HIGHEST_PRECEDENCE;
  }

  private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
    ServerHttpResponse response = exchange.getResponse();
    response.setStatusCode(httpStatus);
    return response.setComplete();
  }

  private String getAuthHeader(ServerHttpRequest request) {
    return request.getHeaders().getOrEmpty("Authorization").get(0);
  }

  private boolean isAuthMissing(ServerHttpRequest request) {
    return !request.getHeaders().containsKey("Authorization");
  }
}
