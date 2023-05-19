package io.marius.demo.ecommerce.inventory.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectResult;
import io.marius.demo.ecommerce.inventory.entity.FileMetadata;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service("S3Service")
public class S3Service {
  private final AmazonS3 s3client;

  @Value("${aws.s3.bucket.name}")
  private String fileBucketName;

  @Value("${aws.s3.zone}")
  private String s3Zone;

  public S3Service(AmazonS3 amazonS3Client) {
    this.s3client = amazonS3Client;

    System.err.println("Available Buckets:");
    for (Bucket bucket : amazonS3Client.listBuckets()) {
      System.err.println(bucket.getName());
    }
  }

  public PutObjectResult saveBufferedImageToS3(BufferedImage scaledImage, FileMetadata metadata)
      throws IOException {
    byte[] imageBuffer =
        toByteArrayOutputStream(scaledImage, metadata.getExtension()).toByteArray();

    ObjectMetadata data = new ObjectMetadata();
    data.setContentType(String.format("image/%s", metadata.getExtension()));
    data.setContentLength(imageBuffer.length);

    return s3client.putObject(
        fileBucketName, metadata.getFileKey(), new ByteArrayInputStream(imageBuffer), data);
  }

  public void deleteFileByKey(String key) {
    s3client.deleteObject(new DeleteObjectRequest(fileBucketName, key));
  }

  public String getBucketUrl() {
    return String.format("https://%s.%s", fileBucketName, s3Zone);
  }

  private ByteArrayOutputStream toByteArrayOutputStream(BufferedImage scaledImage, String extension)
      throws IOException {
    ByteArrayOutputStream os = new ByteArrayOutputStream();
    ImageIO.write(scaledImage, extension, os);
    return os;
  }
}
