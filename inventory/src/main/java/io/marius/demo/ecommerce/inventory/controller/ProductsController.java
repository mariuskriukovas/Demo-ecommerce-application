package io.marius.demo.ecommerce.inventory.controller;

import io.marius.demo.ecommerce.inventory.model.payload.ProductInput;
import io.marius.demo.ecommerce.inventory.service.ProductService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("products")
public class ProductsController {
  private final ProductService productService;

  public ProductsController(ProductService productService) {
    this.productService = productService;
  }

  @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
  public String createProduct(@ModelAttribute ProductInput productInput) {
    return productService.createProduct(productInput);
  }

  @PutMapping(
      path = "/{id}",
      consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
  public String updateProduct(@PathVariable Long id, @ModelAttribute ProductInput productInput) {
    return productService.updateProduct(id, productInput);
  }
}
