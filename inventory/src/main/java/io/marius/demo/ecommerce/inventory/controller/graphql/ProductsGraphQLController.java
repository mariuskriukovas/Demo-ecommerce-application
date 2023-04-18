package io.marius.demo.ecommerce.inventory.controller.graphql;

import io.marius.demo.ecommerce.inventory.entity.Product;
import io.marius.demo.ecommerce.inventory.model.payload.ProductInput;
import io.marius.demo.ecommerce.inventory.model.query.ProductFilter;
import io.marius.demo.ecommerce.inventory.service.ProductService;
import java.util.List;
import java.util.Optional;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

@Controller
public class ProductsGraphQLController {
  private final ProductService productService;

  public ProductsGraphQLController(ProductService productService) {
    this.productService = productService;
  }

  @QueryMapping(value = "product")
  public Optional<Product> getProduct(@Argument(name = "id") Long id) {
    return productService.findProduct(id);
  }

  @QueryMapping(value = "allProducts")
  @Transactional(readOnly = true)
  public List<Product> getAllProducts(@Argument(name = "filter") ProductFilter filter) {
    return productService.findAllProducts(filter);
  }

  @MutationMapping(name = "createProduct")
  public String createProduct(@Argument(name = "product") ProductInput product) {
    return productService.createProduct(product);
  }
}
