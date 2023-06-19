package io.marius.demo.ecommerce.inventory.model.payload;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public class ProductCreationPayload extends BaseProductPayload {
  List<MultipartFile> files;

  public List<MultipartFile> getFiles() {
    return files;
  }

  public void setFiles(List<MultipartFile> files) {
    this.files = files;
  }
}
