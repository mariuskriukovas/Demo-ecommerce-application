package io.marius.demo.ecommerce.inventory.controller;

import io.marius.demo.ecommerce.inventory.model.payload.BaseProductPayload;
import io.marius.demo.ecommerce.inventory.model.payload.ProductCreationPayload;
import io.marius.demo.ecommerce.inventory.service.ProductService;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("products")
public class ProductsController {
  private final ProductService productService;

  public ProductsController(ProductService productService) {
    this.productService = productService;
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
  public String createProduct(@ModelAttribute ProductCreationPayload payload) {
    return productService.createProduct(payload);
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @PutMapping("/{id}")
  public String updateProduct(
      @PathVariable Long id, @Validated @RequestBody BaseProductPayload payload) {
    return productService.updateProduct(id, payload);
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @PostMapping(
      path = "/{id}/files",
      consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
  public String uploadProductFiles(
      @PathVariable Long id, @ModelAttribute List<MultipartFile> files) {
    return productService.uploadProductFiles(id, files);
  }
}
