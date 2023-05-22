package io.marius.demo.ecommerce.inventory.helper;

import io.marius.demo.ecommerce.inventory.entity.FileMetadata;
import io.marius.demo.ecommerce.inventory.entity.Product;
import io.marius.demo.ecommerce.inventory.entity.ProductFile;

public class ProductTestHelper {
  public String PRODUCT_FILE_UID = "d9f92aee-f87b-11ed-b67e-0242ac120002";

  public ProductFile getTestProductFile(Product product, FileMetadata fileMetadata) {
    ProductFile testProductFile = new ProductFile();

    testProductFile.setUid(PRODUCT_FILE_UID);
    testProductFile.setProduct(product);
    testProductFile.setFileMetadata(fileMetadata);
    return testProductFile;
  }
}
