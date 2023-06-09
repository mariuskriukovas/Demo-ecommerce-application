package io.marius.demo.ecommerce.inventory.service;

import io.marius.demo.ecommerce.inventory.entity.ProductFile;
import io.marius.demo.ecommerce.inventory.repository.ProductFileRepository;
import jakarta.validation.ValidationException;
import java.io.IOException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("ProductFileService")
public class ProductFileService {
  private final ProductFileRepository productFileRepository;
  private final ImageService imageService;

  public ProductFileService(
      ProductFileRepository productFileRepository, ImageService imageService) {
    this.productFileRepository = productFileRepository;
    this.imageService = imageService;
  }

  @Transactional
  public String deleteProductFile(Long id) {
    ProductFile productFile =
        productFileRepository
            .findById(id)
            .orElseThrow(() -> new ValidationException("Product file not found !"));

    try {
      imageService.removeFile(productFile.getFileMetadata());
    } catch (IOException e) {
      throw new ValidationException("Error deleting images");
    }
    return String.format("Successfully deleted product file with id: %d", id);
  }
}
