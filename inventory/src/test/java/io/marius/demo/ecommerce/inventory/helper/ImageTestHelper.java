package io.marius.demo.ecommerce.inventory.helper;

import io.marius.demo.ecommerce.inventory.entity.FileMetadata;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageTestHelper {
  public BufferedImage getTestImage() throws IOException {
    String testImagePath = "src/test/resources/nokia_test.png";

    File testImageFile = new File(testImagePath);
    return ImageIO.read(testImageFile);
  }

  public FileMetadata getTestFileMetadata() {
    return FileMetadata.FileMetadataBuilder.aFileMetadata()
        .withFileName("nokia_test.png")
        .withKey("2f174cea-f566-11ed-a05b-0242ac120003.png")
        .withExtension("png")
        .withS3Url(
            "https://test_bucket.test_zone.amazonaws.com/2f174cea-f566-11ed-a05b-0242ac120003.png")
        .build();
  }
}
