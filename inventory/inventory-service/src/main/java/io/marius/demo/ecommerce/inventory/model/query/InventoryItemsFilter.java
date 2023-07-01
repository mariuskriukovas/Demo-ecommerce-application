package io.marius.demo.ecommerce.inventory.model.query;

public class InventoryItemsFilter {
  Integer quantityFrom;
  Integer quantityTo;
  ProductFilter productFilter;

  public ProductFilter getProductFilter() {
    return productFilter;
  }

  public void setProductFilter(ProductFilter productFilter) {
    this.productFilter = productFilter;
  }

  public Integer getQuantityFrom() {
    return quantityFrom;
  }

  public void setQuantityFrom(Integer quantityFrom) {
    this.quantityFrom = quantityFrom;
  }

  public Integer getQuantityTo() {
    return quantityTo;
  }

  public void setQuantityTo(Integer quantityTo) {
    this.quantityTo = quantityTo;
  }
}
