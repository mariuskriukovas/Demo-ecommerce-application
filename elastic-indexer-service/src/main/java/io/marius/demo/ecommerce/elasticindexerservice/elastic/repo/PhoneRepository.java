package io.marius.demo.ecommerce.elasticindexerservice.elastic.repo;

import io.marius.demo.ecommerce.elasticindexerservice.elastic.document.PhoneIndex;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface PhoneRepository extends ElasticsearchRepository<PhoneIndex, String> {}
