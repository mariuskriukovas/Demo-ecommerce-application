package io.marius.demo.ecommerce.inventory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import io.marius.demo.ecommerce.inventory.entity.FileMetadata;
import io.marius.demo.ecommerce.inventory.helper.ImageTestHelper;
import io.marius.demo.ecommerce.inventory.service.S3Service;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class S3ServiceTest {
  private final ImageTestHelper imageTestHelper = new ImageTestHelper();
  private final String testBucketName = "test-bucket";
  private final String testS3Zone = "test_zone.amazonaws.com";
  private AmazonS3 s3client;
  private S3Service s3Service;

  @BeforeAll
  public void setUp() {
    s3client = Mockito.mock(AmazonS3.class);

    when(s3client.listBuckets()).thenReturn(List.of(new Bucket(testBucketName)));
    s3Service = new S3Service(s3client);

    ReflectionTestUtils.setField(s3Service, "fileBucketName", testBucketName);
    ReflectionTestUtils.setField(s3Service, "s3Zone", testS3Zone);
  }

  @Test
  public void testGetBucketUrl() {
    assertEquals(s3Service.getBucketUrl(), "https://test-bucket.test_zone.amazonaws.com");
  }

  @Test
  public void testSaveBufferedImageToS3() throws IOException {
    BufferedImage testImage = imageTestHelper.getTestImage();
    FileMetadata testFileMetadata = imageTestHelper.getTestFileMetadata();

    ArgumentCaptor<String> bucketNameCaptor = ArgumentCaptor.forClass(String.class);
    ArgumentCaptor<String> keyCaptor = ArgumentCaptor.forClass(String.class);
    ArgumentCaptor<ByteArrayInputStream> inputStreamCaptor =
        ArgumentCaptor.forClass(ByteArrayInputStream.class);
    ArgumentCaptor<ObjectMetadata> metadataCaptor = ArgumentCaptor.forClass(ObjectMetadata.class);

    s3Service.saveBufferedImageToS3(testImage, testFileMetadata);

    verify(s3client)
        .putObject(
            bucketNameCaptor.capture(),
            keyCaptor.capture(),
            inputStreamCaptor.capture(),
            metadataCaptor.capture());

    assertEquals(bucketNameCaptor.getValue(), testBucketName);
    assertEquals(keyCaptor.getValue(), "2f174cea-f566-11ed-a05b-0242ac120003.png");
    assertTrue(inputStreamCaptor.getValue().readAllBytes().length > 0);
    assertEquals(metadataCaptor.getValue().getContentType(), "image/png");
  }

  @Test
  public void testDeleteFileByKey() throws IOException {
    String testKey = "2f174cea-f566-11ed-a05b-0242ac120003.png";
    s3Service.deleteFileByKey(testKey);

    ArgumentCaptor<DeleteObjectRequest> deleteObjectRequestCaptor =
        ArgumentCaptor.forClass(DeleteObjectRequest.class);

    verify(s3client).deleteObject(deleteObjectRequestCaptor.capture());

    assertEquals(deleteObjectRequestCaptor.getValue().getBucketName(), testBucketName);
    assertEquals(deleteObjectRequestCaptor.getValue().getKey(), testKey);
  }
}
