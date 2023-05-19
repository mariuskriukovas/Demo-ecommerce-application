package io.marius.demo.ecommerce.inventory.helper;

import io.marius.demo.ecommerce.inventory.entity.FileMetadata;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

public class ImageTestHelper {
  public MultipartFile getTestMultipartFile() throws IOException {
    BufferedImage testImage = getTestImage();

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    ImageIO.write(testImage, "png", baos);
    baos.flush();

    return new MockMultipartFile(
        "nokia_test.png", "nokia_test.png", "image/png", baos.toByteArray());
  }

  public BufferedImage getTestImage() throws IOException {
    String testImagePath = "src/test/resources/nokia_test.png";

    File testImageFile = new File(testImagePath);
    return ImageIO.read(testImageFile);
  }

  public FileMetadata getTestFileMetadata() {
    return FileMetadata.FileMetadataBuilder.aFileMetadata()
        .withFileName("nokia_test.png")
        .withFileKey("2f174cea-f566-11ed-a05b-0242ac120003.png")
        .withExtension("png")
        .withS3Url(
            "https://test_bucket.test_zone.amazonaws.com/2f174cea-f566-11ed-a05b-0242ac120003.png")
        .build();
  }
}
