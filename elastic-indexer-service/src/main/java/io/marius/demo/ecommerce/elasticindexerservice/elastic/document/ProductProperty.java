package io.marius.demo.ecommerce.elasticindexerservice.elastic.document;

import io.marius.demo.ecommerce.inventory.api.entity.BaseProductProperty;

public record ProductProperty(String name, String description) implements BaseProductProperty {
  @Override
  public String getName() {
    return name;
  }

  @Override
  public String getDescription() {
    return description;
  }
}
