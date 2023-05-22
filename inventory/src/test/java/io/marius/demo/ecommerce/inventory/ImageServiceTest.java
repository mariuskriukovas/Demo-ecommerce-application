package io.marius.demo.ecommerce.inventory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.amazonaws.services.s3.model.PutObjectResult;
import io.marius.demo.ecommerce.inventory.entity.FileMetadata;
import io.marius.demo.ecommerce.inventory.helper.ImageTestHelper;
import io.marius.demo.ecommerce.inventory.repository.FileMetadataRepository;
import io.marius.demo.ecommerce.inventory.service.ImageService;
import io.marius.demo.ecommerce.inventory.service.S3Service;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.UUID;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ImageServiceTest {
  private final ImageTestHelper imageTestHelper = new ImageTestHelper();
  private S3Service s3Service;
  private FileMetadataRepository fileMetadataRepository;
  private ImageService imageService;

  @BeforeAll
  public void setUp() {
    fileMetadataRepository = Mockito.mock(FileMetadataRepository.class);
    s3Service = Mockito.mock(S3Service.class);

    imageService = new ImageService(fileMetadataRepository, s3Service);
  }

  @Test
  public void testSaveFile() throws IOException {
    MultipartFile testMultipartFile = imageTestHelper.getTestMultipartFile();

    ArgumentCaptor<BufferedImage> bufferedImageCaptor =
        ArgumentCaptor.forClass(BufferedImage.class);

    when(fileMetadataRepository.save(any()))
        .thenAnswer(
            i -> {
              FileMetadata metadata = i.getArgument(0);
              metadata.setId(1L);
              return metadata;
            });

    when(s3Service.saveBufferedImageToS3(any(), any())).thenReturn(new PutObjectResult());
    when(s3Service.getBucketUrl()).thenReturn("https://test-bucket.test_zone.amazonaws.com");

    FileMetadata resultMetadata = imageService.saveFile(testMultipartFile);

    verify(s3Service).saveBufferedImageToS3(bufferedImageCaptor.capture(), any());

    // test image scaling
    // original image size 1,261×2,319
    assertTrue(bufferedImageCaptor.getValue().getHeight() <= 500);
    assertTrue(bufferedImageCaptor.getValue().getWidth() <= 500);

    // test metadata
    assertEquals(resultMetadata.getFileName(), "nokia_test.png");
    assertEquals(resultMetadata.getExtension(), "png");

    String keyUid = resultMetadata.getFileKey().replace(".png", "");
    assertEquals(UUID.fromString(keyUid).toString(), keyUid);
    assertEquals(
        resultMetadata.getS3Url(),
        String.format(
            "%s/%s", "https://test-bucket.test_zone.amazonaws.com", resultMetadata.getFileKey()));
  }

  @Test
  public void testRemoveFile() throws IOException {
    FileMetadata testFileMetadata = imageTestHelper.getTestFileMetadata();
    imageService.removeFile(testFileMetadata);

    ArgumentCaptor<String> fileKeyCaptor = ArgumentCaptor.forClass(String.class);
    ArgumentCaptor<FileMetadata> fileMetadataCaptor = ArgumentCaptor.forClass(FileMetadata.class);

    verify(s3Service).deleteFileByKey(fileKeyCaptor.capture());
    verify(fileMetadataRepository).delete(fileMetadataCaptor.capture());

    assertEquals(fileKeyCaptor.getValue(), "2f174cea-f566-11ed-a05b-0242ac120003.png");
    assertEquals(testFileMetadata, fileMetadataCaptor.getValue());
  }
}
