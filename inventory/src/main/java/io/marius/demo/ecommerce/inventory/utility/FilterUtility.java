package io.marius.demo.ecommerce.inventory.utility;

import java.util.List;

public class FilterUtility {
  public static boolean isValidFilter(String filter) {
    return filter != null && !filter.isEmpty() && !filter.trim().isEmpty();
  }

  public static <T> boolean isValidFilter(List<T> filter) {
    return filter != null && !filter.isEmpty();
  }
}
