package io.marius.demo.ecommerce.inventory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.marius.demo.ecommerce.inventory.entity.FileMetadata;
import io.marius.demo.ecommerce.inventory.entity.Product;
import io.marius.demo.ecommerce.inventory.entity.ProductFile;
import io.marius.demo.ecommerce.inventory.helper.ImageTestHelper;
import io.marius.demo.ecommerce.inventory.helper.ProductTestHelper;
import io.marius.demo.ecommerce.inventory.repository.FileMetadataRepository;
import io.marius.demo.ecommerce.inventory.repository.ProductFileRepository;
import io.marius.demo.ecommerce.inventory.repository.ProductRepository;
import io.marius.demo.ecommerce.inventory.service.ProductFileService;
import io.marius.demo.ecommerce.inventory.service.S3Service;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.*;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles(profiles = {"test"})
@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(roles = "USER")
class ProductFileServiceTest {
  private final ImageTestHelper imageTestHelper = new ImageTestHelper();
  private final ProductTestHelper productTestHelper = new ProductTestHelper();
  private final String TEST_PRODUCT_UID = "2e6e9b48-f86a-11ed-b67e-0242ac120002";

  @MockBean S3Service s3Service;
  @Autowired private ProductFileService productFileService;
  @Autowired private ProductFileRepository productFileRepository;
  @Autowired private FileMetadataRepository fileMetadataRepository;
  @Autowired private ProductRepository productRepository;
  @Autowired private PlatformTransactionManager platformTransactionManager;

  private TransactionTemplate transactionTemplate;

  @Autowired private Flyway flyway;

  @BeforeAll
  public void setUpBeforeAll() {
    flyway.migrate();
  }

  private void prepareData() {
    Product product = productRepository.findByUid(TEST_PRODUCT_UID).orElseThrow();
    FileMetadata metadata = imageTestHelper.getTestFileMetadata();
    metadata.setProductFiles(List.of(productTestHelper.getTestProductFile(product, metadata)));
    fileMetadataRepository.save(metadata);
  }

  private void removeData() {
    Optional<FileMetadata> fileMetadata =
        fileMetadataRepository.findByUid(imageTestHelper.getTestFileMetadata().getUid());

    fileMetadata.ifPresent(metadata -> fileMetadataRepository.delete(metadata));
  }

  @BeforeEach
  @Transactional
  public void setUpBeforeEach() {
    this.prepareData();
    this.transactionTemplate = new TransactionTemplate(platformTransactionManager);
  }

  @AfterEach
  @Transactional
  public void setUpAfterEach() {
    this.removeData();
  }

  private String executeTransactionalCallback(TransactionCallback<String> callback)
      throws InterruptedException, ExecutionException {
    ExecutorService executorService = Executors.newFixedThreadPool(1);
    Future<String> result = executorService.submit(() -> transactionTemplate.execute(callback));

    executorService.shutdown();
    executorService.awaitTermination(10, TimeUnit.SECONDS);

    return result.get();
  }

  @Test
  public void testDeleteProductFile() throws InterruptedException, ExecutionException {
    ProductFile testProductFile =
        productFileRepository.findByUid(productTestHelper.PRODUCT_FILE_UID).orElseThrow();

    String deleteProductFileResult =
        executeTransactionalCallback(
            status -> productFileService.deleteProductFile(testProductFile.getId()));

    Optional<ProductFile> productFile =
        productFileRepository.findByUid(productTestHelper.PRODUCT_FILE_UID);

    Optional<FileMetadata> fileMetadata =
        fileMetadataRepository.findByUid(imageTestHelper.FILE_METADATA_UID);

    assertEquals(
        deleteProductFileResult,
        String.format("Successfully deleted product file with id: %d", testProductFile.getId()));
    assertTrue(productFile.isEmpty());
    assertTrue(fileMetadata.isEmpty());
  }
}
