package io.marius.demo.ecommerce.elasticindexerservice.config;

import com.poiji.bind.Poiji;
import io.marius.demo.ecommerce.elasticindexerservice.elastic.document.PhoneProduct;
import io.marius.demo.ecommerce.elasticindexerservice.elastic.repo.PhoneRepository;
import io.marius.demo.ecommerce.elasticindexerservice.sheet.PhoneDataRow;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class IndexerConfig {
  private final PhoneRepository phoneRepository;

  @Value("${indexer.initialization.filepath}")
  private String filepath;

  public IndexerConfig(PhoneRepository phoneRepository) {
    this.phoneRepository = phoneRepository;
  }

  @Bean
  @ConditionalOnProperty(prefix = "indexer", name = "initialization.enabled", havingValue = "true")
  void initElasticDataset() throws IOException {
    if (phoneRepository.count() == 0) {
      ClassPathResource resource = new ClassPathResource(filepath);
      List<PhoneDataRow> phoneDataRows = Poiji.fromExcel(resource.getFile(), PhoneDataRow.class);

      List<PhoneProduct> items =
          phoneDataRows.stream().map(PhoneProduct::new).collect(Collectors.toList());

      phoneRepository.saveAll(items);

      System.err.printf("Successfully indexed: %d items \n", phoneDataRows.size());
    }
  }
}
