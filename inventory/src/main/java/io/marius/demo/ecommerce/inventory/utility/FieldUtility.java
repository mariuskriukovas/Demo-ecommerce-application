package io.marius.demo.ecommerce.inventory.utility;

import java.util.List;

public class FieldUtility {
  public static boolean isFieldValid(String field) {
    return field != null && !field.isEmpty() && !field.trim().isEmpty();
  }

  public static <T> boolean isFieldValid(List<T> field) {
    return field != null && !field.isEmpty();
  }
}
