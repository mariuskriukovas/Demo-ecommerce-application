package io.marius.demo.ecommerce.inventory.controller.graphql;

import io.marius.demo.ecommerce.inventory.model.payload.PageablePayload;
import io.marius.demo.ecommerce.inventory.model.query.ProductFilter;
import io.marius.demo.ecommerce.inventory.model.view.ProductView;
import io.marius.demo.ecommerce.inventory.service.ProductService;
import java.util.List;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
public class ProductsGraphqlController {
  private final ProductService productService;

  public ProductsGraphqlController(ProductService productService) {
    this.productService = productService;
  }

  @QueryMapping(value = "product")
  public ProductView getProduct(@Argument(name = "id") Long id) {
    return productService.findProduct(id);
  }

  @QueryMapping(value = "allProducts")
  public List<ProductView> getAllProducts(@Argument(name = "filter") ProductFilter filter) {
    return productService.findAllProducts(filter);
  }

  @QueryMapping(value = "allPublicProducts")
  public List<ProductView> getAllPublicProducts(
      @Argument(name = "searchBox") String searchBox,
      @Argument(name = "pageable") PageablePayload pageable) {
    return productService.findAllPublicProducts(searchBox, pageable);
  }
}
