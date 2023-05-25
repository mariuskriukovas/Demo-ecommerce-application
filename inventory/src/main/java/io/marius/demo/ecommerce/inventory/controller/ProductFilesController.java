package io.marius.demo.ecommerce.inventory.controller;

import io.marius.demo.ecommerce.inventory.service.ProductFileService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("productFiles")
public class ProductFilesController {
  private final ProductFileService productFileService;

  public ProductFilesController(ProductFileService productFileService) {
    this.productFileService = productFileService;
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @DeleteMapping("/{id}")
  public String deleteProductFile(@PathVariable Long id) {
    return productFileService.deleteProductFile(id);
  }
}
