package io.marius.demo.ecommerce.accountservice.security.enums;

import io.marius.demo.ecommerce.accountservice.security.enums.converter.AbstractEnumConverter;

public enum UserRole implements PersistableEnum<String> {
  USER("USER"),
  MANAGER("MANAGER"),
  ADMIN("ADMIN");
  private final String value;

  UserRole(String value) {
    this.value = value;
  }

  @Override
  public String getValue() {
    return value;
  }

  public static class Converter extends AbstractEnumConverter<UserRole, String> {
    public Converter() {
      super(UserRole.class);
    }
  }
}
