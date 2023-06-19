package io.marius.demo.ecommerce.elasticindexerservice;

import io.marius.demo.ecommerce.inventory.api.client.ClassifiersClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackageClasses = {ClassifiersClient.class})
public class ElasticIndexerServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(ElasticIndexerServiceApplication.class, args);
  }
}
