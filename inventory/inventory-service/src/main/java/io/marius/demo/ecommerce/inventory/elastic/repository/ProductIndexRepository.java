package io.marius.demo.ecommerce.inventory.elastic.repository;

import io.marius.demo.ecommerce.inventory.elastic.entity.ProductIndex;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ProductIndexRepository extends ElasticsearchRepository<ProductIndex, String> {
  @Query(
      "{"
          + "\"bool\": {"
          + "      \"should\": ["
          + "         {"
          + "          \"match\": {"
          + "            \"name\": \"?0\""
          + "          }"
          + "        },"
          + "        {"
          + "          \"match\": {"
          + "            \"properties.description\": \"?0\""
          + "          }"
          + "        },"
          + "        {"
          + "          \"match\": {"
          + "            \"description\": \"?0\""
          + "          }"
          + "        }"
          + "      ]"
          + "    }"
          + "    }")
  Page<ProductIndex> findAllBySearchBox(String searchBox, Pageable pageable);
}
