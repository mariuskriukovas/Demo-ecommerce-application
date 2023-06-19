package io.marius.demo.ecommerce.accountservice.provider;

import java.util.Collections;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Component;

@Component
public class RegisteredClientAuthProvider implements AuthenticationProvider {
  private final RegisteredClientRepository registeredClientRepository;

  public RegisteredClientAuthProvider(RegisteredClientRepository registeredClientRepository) {
    this.registeredClientRepository = registeredClientRepository;
  }

  @Override
  public Authentication authenticate(Authentication auth) throws AuthenticationException {
    String username = auth.getName();
    String password = auth.getCredentials().toString();

    RegisteredClient client = registeredClientRepository.findByClientId(username);

    if (client != null && client.getClientSecret().equals(password)) {
      return new UsernamePasswordAuthenticationToken(username, password, Collections.emptyList());
    } else {
      throw new BadCredentialsException("External system authentication failed");
    }
  }

  @Override
  public boolean supports(Class<?> auth) {
    return auth.equals(UsernamePasswordAuthenticationToken.class);
  }
}
