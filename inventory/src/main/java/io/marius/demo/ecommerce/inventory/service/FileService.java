package io.marius.demo.ecommerce.inventory.service;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectResult;
import io.marius.demo.ecommerce.inventory.entity.File;
import io.marius.demo.ecommerce.inventory.repository.FileRepository;
import java.io.IOException;
import java.util.UUID;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service("FileService")
public class FileService {
  private final FileRepository fileRepository;
  private final AmazonS3 s3client;

  @Value("${aws.s3.bucket.name}")
  private String fileBucketName;

  @Value("${aws.s3.zone}")
  private String s3Zone;

  public FileService(FileRepository fileRepository, AmazonS3 amazonS3Client) {
    this.fileRepository = fileRepository;
    this.s3client = amazonS3Client;

    System.err.println("Available Buckets:");
    for (Bucket bucket : amazonS3Client.listBuckets()) {
      System.err.println(bucket.getName());
    }
  }

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public File saveFile(MultipartFile multipartFile) throws SdkClientException, IOException {
    ObjectMetadata data = new ObjectMetadata();
    data.setContentType(multipartFile.getContentType());
    data.setContentLength(multipartFile.getSize());

    String extension = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
    String uniqFilename = String.format("%s.%s", UUID.randomUUID(), extension);

    PutObjectResult uploadResult =
        s3client.putObject(fileBucketName, uniqFilename, multipartFile.getInputStream(), data);

    return fileRepository.save(
        buildFileEntity(uploadResult, uniqFilename, multipartFile.getOriginalFilename()));
  }

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void removeFile(File file) throws SdkClientException, IOException {
    s3client.deleteObject(new DeleteObjectRequest(fileBucketName, file.getKey()));
    fileRepository.delete(file);
  }

  private File buildFileEntity(PutObjectResult input, String uniqName, String originalName) {
    String s3Url = String.format("https://%s.%s/%s", fileBucketName, s3Zone, uniqName);
    return File.FileBuilder.aFile()
        .withFileName(originalName)
        .withKey(uniqName)
        .withS3Url(s3Url)
        .build();
  }
}
