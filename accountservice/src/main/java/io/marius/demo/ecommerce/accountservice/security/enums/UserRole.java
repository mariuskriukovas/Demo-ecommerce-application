package io.marius.demo.ecommerce.accountservice.security.enums;

public enum UserRole {
  /**
   * USER - regular user with privilege to see item listings and buy stuff MANAGER - USER privileges
   * + in addition ability to maintain inventor ADMIN - all privileges
   */
  USER,
  MANAGER,
  ADMIN
}
