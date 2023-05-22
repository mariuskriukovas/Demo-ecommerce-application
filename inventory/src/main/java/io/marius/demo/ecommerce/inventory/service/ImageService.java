package io.marius.demo.ecommerce.inventory.service;

import com.amazonaws.SdkClientException;
import io.marius.demo.ecommerce.inventory.entity.FileMetadata;
import io.marius.demo.ecommerce.inventory.repository.FileMetadataRepository;
import jakarta.validation.ValidationException;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import javax.imageio.ImageIO;
import org.apache.commons.io.FilenameUtils;
import org.imgscalr.Scalr;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service("ImageService")
public class ImageService {
  private final int TARGET_IMAGE_SIZE = 500;
  private final List<String> SUPPORTED_FILE_EXTENSIONS =
      List.of("gif", "png", "jpeg", "bmp", "wbmp");
  private final S3Service s3Service;
  private final FileMetadataRepository fileMetadataRepository;

  public ImageService(FileMetadataRepository fileMetadataRepository, S3Service s3Service) {
    this.fileMetadataRepository = fileMetadataRepository;
    this.s3Service = s3Service;
  }

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public FileMetadata saveFile(MultipartFile multipartFile) throws SdkClientException, IOException {
    String extension = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
    checkIfFileSupported(extension);

    BufferedImage originalImage = ImageIO.read(multipartFile.getInputStream());
    BufferedImage scaledImage = resizeImage(originalImage, TARGET_IMAGE_SIZE);

    String uniqFilename = String.format("%s.%s", UUID.randomUUID(), extension);
    FileMetadata metadata =
        fileMetadataRepository.save(
            buildFileEntity(uniqFilename, multipartFile.getOriginalFilename(), extension));

    s3Service.saveBufferedImageToS3(scaledImage, metadata);
    return metadata;
  }

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void removeFile(FileMetadata fileMetadata) throws SdkClientException, IOException {
    s3Service.deleteFileByKey(fileMetadata.getFileKey());
    fileMetadataRepository.delete(fileMetadata);
  }

  private void checkIfFileSupported(String extension) {
    if (SUPPORTED_FILE_EXTENSIONS.stream().noneMatch(e -> e.equals(extension))) {
      throw new ValidationException("Error, wrong image format");
    }
  }

  private BufferedImage resizeImage(BufferedImage image, int targetSize) {
    return Scalr.resize(image, targetSize);
  }

  private FileMetadata buildFileEntity(String uniqName, String originalName, String extension) {
    String s3Url = String.format("%s/%s", s3Service.getBucketUrl(), uniqName);
    return FileMetadata.FileMetadataBuilder.aFileMetadata()
        .withFileName(originalName)
        .withExtension(extension)
        .withFileKey(uniqName)
        .withS3Url(s3Url)
        .build();
  }
}
