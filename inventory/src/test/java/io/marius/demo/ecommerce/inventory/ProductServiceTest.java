package io.marius.demo.ecommerce.inventory;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.marius.demo.ecommerce.inventory.entity.Product;
import io.marius.demo.ecommerce.inventory.helper.ImageTestHelper;
import io.marius.demo.ecommerce.inventory.helper.ProductTestHelper;
import io.marius.demo.ecommerce.inventory.mapper.ClassifierMapperImpl;
import io.marius.demo.ecommerce.inventory.mapper.ProductMapper;
import io.marius.demo.ecommerce.inventory.mapper.ProductMapperImpl;
import io.marius.demo.ecommerce.inventory.model.view.ProductView;
import io.marius.demo.ecommerce.inventory.repository.ProductCategoryRepository;
import io.marius.demo.ecommerce.inventory.repository.ProductPropertyRepository;
import io.marius.demo.ecommerce.inventory.repository.ProductRepository;
import io.marius.demo.ecommerce.inventory.service.ImageService;
import io.marius.demo.ecommerce.inventory.service.ProductService;
import io.marius.demo.ecommerce.inventory.service.predicates.ProductPredicate;
import jakarta.persistence.EntityManager;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = {ClassifierMapperImpl.class, ProductMapperImpl.class})
class ProductServiceTest {
  private final ProductTestHelper productTestHelper = new ProductTestHelper();
  private final ImageTestHelper imageTestHelper = new ImageTestHelper();

  @MockBean private ProductPropertyRepository productPropertyRepository;
  private ProductRepository productRepository;
  private ProductCategoryRepository productCategoryRepository;

  @Autowired private ProductMapper productMapper;
  private ImageService imageService;
  private ProductService productService;

  @BeforeEach
  public void setUp(@Mock EntityManager entityManager, @Mock ProductPredicate productPredicate) {
    productRepository = Mockito.mock(ProductRepository.class);
    productCategoryRepository = Mockito.mock(ProductCategoryRepository.class);
    imageService = Mockito.mock(ImageService.class);

    productService =
        new ProductService(
            productRepository,
            productCategoryRepository,
            entityManager,
            productMapper,
            productPredicate,
            imageService);
  }

  @Test
  void testCreateProductWithoutFiles() {
    when(productCategoryRepository.findByName("Laptop"))
        .thenReturn(Optional.of(productTestHelper.getTestLaptopCategory()));

    when(productRepository.save(any()))
        .thenAnswer(
            i -> {
              Product product = i.getArgument(0);

              product.setId(1L);
              product.setUid("b7e3f954-f93c-11ed-be56-0242ac120002");
              product.getProperties().forEach(productTestHelper::setTestIdentifiers);

              return product;
            });

    ArgumentCaptor<Product> productCaptor = ArgumentCaptor.forClass(Product.class);

    String createProductResult =
        productService.createProduct(productTestHelper.getTestProductCreationPayload(null));

    verify(productRepository).save(productCaptor.capture());

    assertEquals(
        productCaptor.getValue().getProductCategory().getUid(),
        "fdf2545a-f869-11ed-b67e-0242ac120002");
    assertEquals(productCaptor.getValue().getProperties().size(), 1);
    assertEquals(
        productCaptor.getValue().getProperties().get(0).getUid(),
        "fe6bfe68-f93b-11ed-be56-0242ac120002");
    assertNull(productCaptor.getValue().getProductFiles());

    assertEquals(createProductResult, "Successfully saved product with id: 1");
  }

  @Test
  void testCreateProductWithFiles() throws IOException {
    when(productCategoryRepository.findByName("Laptop"))
        .thenReturn(Optional.of(productTestHelper.getTestLaptopCategory()));

    when(productRepository.save(any()))
        .thenAnswer(
            i -> {
              Product product = i.getArgument(0);

              product.setId(1L);
              product.setUid("b7e3f954-f93c-11ed-be56-0242ac120002");
              product.getProductFiles().forEach(productTestHelper::setTestIdentifiers);
              return product;
            });

    when(imageService.saveFile(any())).thenReturn(imageTestHelper.getTestFileMetadata());

    ArgumentCaptor<Product> productCaptor = ArgumentCaptor.forClass(Product.class);

    String createProductResult =
        productService.createProduct(
            productTestHelper.getTestProductCreationPayload(
                List.of(imageTestHelper.getTestMultipartFile())));

    verify(productRepository).save(productCaptor.capture());

    assertEquals(productCaptor.getValue().getProductFiles().size(), 1);
    assertEquals(
        productCaptor.getValue().getProductFiles().get(0).getUid(),
        "fe6bfe68-f93b-11ed-be56-0242ac120002");
    assertEquals(
        productCaptor.getValue().getProductFiles().get(0).getFileMetadata().getUid(),
        imageTestHelper.FILE_METADATA_UID);

    assertEquals(createProductResult, "Successfully saved product with id: 1");
  }

  @Test
  void testUpdateProduct() {
    ArgumentCaptor<Product> productCaptor = ArgumentCaptor.forClass(Product.class);

    when(productRepository.findById(1L))
        .thenReturn(Optional.of(productTestHelper.getTestProduct()));

    when(productCategoryRepository.findByName("Phone"))
        .thenReturn(Optional.of(productTestHelper.getTestPhoneCategory()));

    when(productRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);

    String updateProductResult =
        productService.updateProduct(1L, productTestHelper.getTestProductUpdatePayload());

    verify(productRepository).save(productCaptor.capture());

    assertEquals(productCaptor.getValue().getUid(), "b7e3f954-f93c-11ed-be56-0242ac120002");
    assertEquals(productCaptor.getValue().getName(), "TEST_V2");
    assertEquals(productCaptor.getValue().getPrice(), 1999.00);
    assertEquals(productCaptor.getValue().getDescription(), "test_V2");
    assertEquals(
        productCaptor.getValue().getProductCategory().getUid(),
        "fdf2545b-f869-11ed-b67e-0242ac120002");
    assertEquals(productCaptor.getValue().getProperties().size(), 1);
    assertEquals(productCaptor.getValue().getProperties().get(0).getName(), "test_V2");
    assertNull(productCaptor.getValue().getProductFiles());

    assertEquals(updateProductResult, "Successfully updated product with id: 1");
  }

  @Test
  void testUploadProductFilesWhenNoFilesSavedPreviously() throws IOException {
    ArgumentCaptor<Product> productCaptor = ArgumentCaptor.forClass(Product.class);

    when(productRepository.findById(1L))
        .thenReturn(Optional.of(productTestHelper.getTestProduct()));

    when(imageService.saveFile(any())).thenReturn(imageTestHelper.getTestFileMetadata());

    when(productRepository.save(any()))
        .thenAnswer(
            i -> {
              Product product = i.getArgument(0);
              product.getProductFiles().forEach(productTestHelper::setTestIdentifiers);
              return product;
            });

    String updateProductResult =
        productService.uploadProductFiles(1L, List.of(imageTestHelper.getTestMultipartFile()));

    verify(productRepository).save(productCaptor.capture());

    assertEquals(productCaptor.getValue().getProductFiles().size(), 1);
    assertEquals(
        productCaptor.getValue().getProductFiles().get(0).getUid(),
        "fe6bfe68-f93b-11ed-be56-0242ac120002");
    assertEquals(
        productCaptor.getValue().getProductFiles().get(0).getFileMetadata().getUid(),
        imageTestHelper.FILE_METADATA_UID);
    assertEquals(
        productCaptor.getValue().getProductFiles().get(0).getProduct().getUid(),
        "b7e3f954-f93c-11ed-be56-0242ac120002");

    assertEquals(updateProductResult, "Successfully updated files for product with id: 1");
  }

  @Test
  void testFindProduct() {
    Product testProduct = productTestHelper.getTestProduct();
    testProduct.setProductFiles(
        List.of(
            productTestHelper.getTestProductFile(
                testProduct, imageTestHelper.getTestFileMetadata())));

    when(productRepository.findById(1L)).thenReturn(Optional.of(testProduct));

    ProductView findProductResult = productService.findProduct(1L);

    assertEquals(findProductResult.getId(), 1L);
    assertEquals(findProductResult.getName(), "TEST");
    assertEquals(findProductResult.getPrice(), 1500.00);
    assertEquals(findProductResult.getDescription(), "test test test test test");
    assertEquals(findProductResult.getProductCategory().getName(), "Laptop");
    assertEquals(findProductResult.getProperties().size(), 1);
    assertEquals(findProductResult.getProperties().get(0).getName(), "test");
    assertEquals(findProductResult.getProductFiles().size(), 1);
    assertEquals(
        findProductResult.getProductFiles().get(0).getS3Url(),
        "https://test_bucket.test_zone.amazonaws.com/2f174cea-f566-11ed-a05b-0242ac120003.png");
  }
}
