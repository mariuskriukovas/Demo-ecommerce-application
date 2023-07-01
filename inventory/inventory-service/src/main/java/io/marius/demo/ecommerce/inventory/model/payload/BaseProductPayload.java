package io.marius.demo.ecommerce.inventory.model.payload;

import io.marius.demo.ecommerce.common.api.view.ClassifierView;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public class BaseProductPayload {
  @NotNull String name;
  @NotNull Double price;
  String description;
  @NotNull ClassifierView productCategory;
  List<PropertyInput> properties;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public List<PropertyInput> getProperties() {
    return properties;
  }

  public void setProperties(List<PropertyInput> properties) {
    this.properties = properties;
  }

  public ClassifierView getProductCategory() {
    return productCategory;
  }

  public void setProductCategory(ClassifierView productCategory) {
    this.productCategory = productCategory;
  }
}
