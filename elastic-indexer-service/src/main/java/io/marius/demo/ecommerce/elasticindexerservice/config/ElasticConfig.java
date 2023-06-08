package io.marius.demo.ecommerce.elasticindexerservice.config;

import io.marius.demo.ecommerce.common.elastic.config.BaseElasticConfig;
import io.marius.demo.ecommerce.elasticindexerservice.elastic.repo.PhoneRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@Import(BaseElasticConfig.class)
@EnableElasticsearchRepositories(basePackageClasses = PhoneRepository.class)
public class ElasticConfig {}
