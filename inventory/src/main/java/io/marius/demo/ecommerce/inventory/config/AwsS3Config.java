package io.marius.demo.ecommerce.inventory.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AwsS3Config {
  @Value("${aws.credentials.accessKey}")
  private String accessKey;

  @Value("${aws.credentials.secretKey}")
  private String secretKey;

  @Bean
  AWSCredentials awsCredentials() {
    return new BasicAWSCredentials(accessKey, secretKey);
  }

  @Bean
  AmazonS3 amazonS3Client(AWSCredentials credentials) {
    return AmazonS3ClientBuilder.standard()
        .withCredentials(new AWSStaticCredentialsProvider(credentials))
        .withRegion(Regions.EU_NORTH_1)
        .build();
  }
}
