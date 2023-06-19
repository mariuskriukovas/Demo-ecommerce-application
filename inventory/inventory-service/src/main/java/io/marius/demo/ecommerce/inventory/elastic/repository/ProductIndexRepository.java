package io.marius.demo.ecommerce.inventory.elastic.repository;

import io.marius.demo.ecommerce.inventory.elastic.entity.ProductIndex;
import java.util.List;
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
  List<ProductIndex> findAllBySearchBox(String searchBox, Pageable pageable);
}
