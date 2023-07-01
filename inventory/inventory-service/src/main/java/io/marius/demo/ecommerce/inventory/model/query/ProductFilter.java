package io.marius.demo.ecommerce.inventory.model.query;

import java.util.List;
import org.springframework.data.domain.Pageable;

public class ProductFilter {
  String name;
  String description;
  String category;
  Double priceFrom;
  Double priceTo;
  List<PropertyFilter> properties;

  Pageable pageable;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public List<PropertyFilter> getProperties() {
    return properties;
  }

  public void setProperties(List<PropertyFilter> properties) {
    this.properties = properties;
  }

  public Double getPriceFrom() {
    return priceFrom;
  }

  public void setPriceFrom(Double priceFrom) {
    this.priceFrom = priceFrom;
  }

  public Double getPriceTo() {
    return priceTo;
  }

  public void setPriceTo(Double priceTo) {
    this.priceTo = priceTo;
  }
}
