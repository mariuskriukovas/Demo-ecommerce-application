package io.marius.demo.ecommerce.inventory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.marius.demo.ecommerce.inventory.entity.Product;
import io.marius.demo.ecommerce.inventory.entity.ProductProperty;
import io.marius.demo.ecommerce.inventory.model.payload.ProductInput;
import io.marius.demo.ecommerce.inventory.model.payload.PropertyInput;
import io.marius.demo.ecommerce.inventory.model.query.ProductFilter;
import io.marius.demo.ecommerce.inventory.model.query.PropertyFilter;
import io.marius.demo.ecommerce.inventory.service.ProductService;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(SpringExtension.class)
@ActiveProfiles(profiles = {"test"})
@SpringBootTest
class ProductServiceTest {

  @Autowired private Flyway flyway;
  @Autowired private ProductService productService;

  @BeforeEach
  public void setUp() {
    flyway.clean();
    flyway.migrate();
  }

  @Test
  @Transactional(readOnly = true)
  void shouldReturnProductDTOWhenProductExists() {

    Product product = productService.findProduct(1L);

    assertEquals("Apple MacBook Air (M2)", product.getName());
    assertEquals("Laptop", product.getProductCategory().getName());

    Optional<ProductProperty> property = product.getProperties().stream().findFirst();

    assertTrue(property.isPresent());
    assertEquals("Color", property.get().getName());
  }

  @Test
  void shouldThrowExceptionWhenProductNotExists() {

    String exceptionMessage = null;
    try {
      productService.findProduct(999L);
    } catch (NoSuchElementException e) {
      exceptionMessage = e.getMessage();
    }

    assertEquals("Product not found", exceptionMessage);
  }

  @Test
  void shouldReturnProductListWhenProductFilterMatchesAllConditions() {
    PropertyFilter property = new PropertyFilter();
    property.setName("Color");
    property.setDescription("MacBook");

    ProductFilter filter = new ProductFilter();
    filter.setName("MacBook");
    filter.setDescription("MacBook");
    filter.setCategory("Laptop");
    filter.setProperties(List.of(property));
    filter.setPriceFrom(1.0);
    filter.setPriceTo(1500.0);

    List<Product> products = productService.findAllProducts(filter);
    assertEquals(1, products.size());
    assertEquals(1L, products.stream().findFirst().get().getId());
  }

  @Test
  void shouldReturnEmptyListWhenProductFilterFailsOneCondition() {

    ProductFilter filter = new ProductFilter();
    filter.setName("MacBookk");

    List<Product> products = productService.findAllProducts(filter);
    assertEquals(0, products.size());
  }

  @Test
  void shouldCreateNewProductWhenValidDataIsProvided() {
    PropertyInput property = new PropertyInput();
    property.setName("CPU");
    property.setDescription("M2");

    ProductInput input = new ProductInput();

    input.setName("MacBook PRO");
    input.setDescription("Best MacBook");
    input.setProductCategory("Laptop");
    input.setProperties(List.of(property));
    input.setPrice(1999.99);

    String msg = productService.createProduct(input);

    assertEquals("Successfully saved product with id: 10", msg);
  }
}
