package io.marius.demo.ecommerce.inventory.helper;

import io.marius.demo.ecommerce.common.api.view.ClassifierView;
import io.marius.demo.ecommerce.common.persistence.entity.BaseEntity;
import io.marius.demo.ecommerce.inventory.entity.*;
import io.marius.demo.ecommerce.inventory.model.payload.BaseProductPayload;
import io.marius.demo.ecommerce.inventory.model.payload.ProductCreationPayload;
import io.marius.demo.ecommerce.inventory.model.payload.PropertyInput;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.web.multipart.MultipartFile;

public class ProductTestHelper {
  public String PRODUCT_FILE_UID = "d9f92aee-f87b-11ed-b67e-0242ac120002";

  public ProductFile getTestProductFile(Product product, FileMetadata fileMetadata) {
    ProductFile testProductFile = new ProductFile();

    testProductFile.setUid(PRODUCT_FILE_UID);
    testProductFile.setProduct(product);
    testProductFile.setFileMetadata(fileMetadata);
    return testProductFile;
  }

  public ProductCategory getTestLaptopCategory() {
    ProductCategory testProductCategory = new ProductCategory();

    testProductCategory.setId(1L);
    testProductCategory.setUid("fdf2545a-f869-11ed-b67e-0242ac120002");
    testProductCategory.setName("Laptop");

    return testProductCategory;
  }

  public ProductCategory getTestPhoneCategory() {
    ProductCategory testProductCategory = new ProductCategory();

    testProductCategory.setId(2L);
    testProductCategory.setUid("fdf2545b-f869-11ed-b67e-0242ac120002");
    testProductCategory.setName("Phone");

    return testProductCategory;
  }

  public ClassifierView getTestLaptopCategoryAsClassifierView() {
    ClassifierView testProductCategory = new ClassifierView();

    testProductCategory.setId(1L);
    testProductCategory.setName("Laptop");

    return testProductCategory;
  }

  public ClassifierView getTestPhoneCategoryAsClassifierView() {
    ClassifierView testProductCategory = new ClassifierView();

    testProductCategory.setId(2L);
    testProductCategory.setName("Phone");

    return testProductCategory;
  }

  public List<PropertyInput> getTestPropertyInputs() {
    PropertyInput testPropertyInput = new PropertyInput();

    testPropertyInput.setName("test");
    testPropertyInput.setDescription("test_test_test");

    return new ArrayList<>(List.of(testPropertyInput));
  }

  public List<ProductProperty> getTestProductProperties() {
    ProductProperty testProductProperty = new ProductProperty();

    setTestIdentifiers(testProductProperty);
    testProductProperty.setName("test");
    testProductProperty.setDescription("test_test_test");

    return new ArrayList<>(List.of(testProductProperty));
  }

  public void setTestIdentifiers(BaseEntity entity) {
    entity.setId(1L);
    entity.setUid("fe6bfe68-f93b-11ed-be56-0242ac120002");
  }

  public ProductCreationPayload getTestProductCreationPayload(List<MultipartFile> files) {
    ProductCreationPayload payload = new ProductCreationPayload();

    payload.setName("TEST");
    payload.setPrice(1500.00);
    payload.setDescription("test test test test test");
    payload.setProductCategory(getTestLaptopCategoryAsClassifierView());
    payload.setProperties(getTestPropertyInputs());
    payload.setFiles(files);

    return payload;
  }

  public BaseProductPayload getTestProductUpdatePayload() {
    BaseProductPayload payload = new ProductCreationPayload();

    List<PropertyInput> propertyInputs =
        getTestPropertyInputs().stream()
            .peek(e -> e.setName("test_V2"))
            .collect(Collectors.toList());

    payload.setName("TEST_V2");
    payload.setPrice(1999.00);
    payload.setDescription("test_V2");
    payload.setProductCategory(getTestPhoneCategoryAsClassifierView());
    payload.setProperties(propertyInputs);

    return payload;
  }

  public Product getTestProduct() {
    Product product = new Product();

    product.setId(1L);
    product.setUid("b7e3f954-f93c-11ed-be56-0242ac120002");
    product.setName("TEST");
    product.setPrice(1500.00);
    product.setDescription("test test test test test");
    product.setProductCategory(getTestLaptopCategory());
    product.setProperties(getTestProductProperties());

    return product;
  }
}
