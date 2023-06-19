package io.marius.demo.ecommerce.inventory.config;

import io.marius.demo.ecommerce.common.elastic.config.BaseElasticConfig;
import io.marius.demo.ecommerce.inventory.elastic.repository.ProductIndexRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@Import(BaseElasticConfig.class)
@EnableElasticsearchRepositories(basePackageClasses = ProductIndexRepository.class)
public class ElasticConfig {}
