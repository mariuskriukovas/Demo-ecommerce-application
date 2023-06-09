package io.marius.demo.ecommerce.inventory.entity;

import io.marius.demo.ecommerce.common.persistence.entity.BaseEntity;
import io.marius.demo.ecommerce.inventory.api.entity.BaseProductFile;
import jakarta.persistence.*;

@Entity
@Table(name = "product_file")
public class ProductFile extends BaseEntity implements BaseProductFile {
  @ManyToOne
  @JoinColumn(name = "product_id", nullable = false)
  private Product product;

  @ManyToOne
  @JoinColumn(name = "file_id", nullable = false)
  private FileMetadata fileMetadata;

  public Product getProduct() {
    return product;
  }

  public void setProduct(Product product) {
    this.product = product;
  }

  public FileMetadata getFileMetadata() {
    return fileMetadata;
  }

  public void setFileMetadata(FileMetadata fileMetadata) {
    this.fileMetadata = fileMetadata;
  }
}
