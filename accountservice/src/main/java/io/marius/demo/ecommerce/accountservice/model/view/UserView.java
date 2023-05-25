package io.marius.demo.ecommerce.accountservice.model.view;

import java.util.List;
import org.springframework.security.core.GrantedAuthority;

public class UserView {
  private String username;
  private List<GrantedAuthority> roles;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public List<GrantedAuthority> getRoles() {
    return roles;
  }

  public void setRoles(List<GrantedAuthority> roles) {
    this.roles = roles;
  }

  public static final class UserViewBuilder {
    private String username;
    private List<GrantedAuthority> roles;

    private UserViewBuilder() {
    }

    public static UserViewBuilder anUserView() {
      return new UserViewBuilder();
    }

    public UserViewBuilder withUsername(String username) {
      this.username = username;
      return this;
    }

    public UserViewBuilder withRoles(List<GrantedAuthority> roles) {
      this.roles = roles;
      return this;
    }

    public UserView build() {
      UserView userView = new UserView();
      userView.setUsername(username);
      userView.setRoles(roles);
      return userView;
    }
  }
}
