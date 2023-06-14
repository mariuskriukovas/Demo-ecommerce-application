package io.marius.demo.ecommerce.elasticindexerservice.controller;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.indices.IndicesStatsRequest;
import java.io.IOException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("stats")
public class StatsController {
  private final ElasticsearchClient elasticsearchClient;

  public StatsController(ElasticsearchClient elasticsearchClient) {
    this.elasticsearchClient = elasticsearchClient;
  }

  @GetMapping("/{index}")
  public String getIndexStats(@PathVariable String index) throws IOException {
    IndicesStatsRequest request = new IndicesStatsRequest.Builder().index(index).build();
    // todo improve format later
    return elasticsearchClient.indices().stats(request).indices().get(index).toString();
  }
}
