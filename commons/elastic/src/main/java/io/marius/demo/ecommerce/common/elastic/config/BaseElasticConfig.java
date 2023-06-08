package io.marius.demo.ecommerce.common.elastic.config;

import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import nl.altindag.ssl.SSLFactory;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BaseElasticConfig {

  @Value("${spring.elasticsearch.username}")
  private String username;

  @Value("${spring.elasticsearch.password}")
  private String password;

  @Value("${spring.elasticsearch.hostname}")
  private String hostname;

  @Value("${spring.elasticsearch.port}")
  private Integer port;

  @Value("${spring.elasticsearch.scheme}")
  private String scheme;

  // TODO do not override RestClient when production SSL is configured
  @Bean
  @ConditionalOnProperty(prefix = "spring", name = "elasticsearch.ssl", havingValue = "false")
  public RestClient getRestClient() {
    CredentialsProvider provider = new BasicCredentialsProvider();
    provider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(username, password));

    SSLFactory sslFactory =
        SSLFactory.builder().withUnsafeTrustMaterial().withUnsafeHostnameVerifier().build();

    return RestClient.builder(new HttpHost(hostname, port, scheme))
        .setHttpClientConfigCallback(
            builder ->
                builder
                    .setSSLContext(sslFactory.getSslContext())
                    .setDefaultCredentialsProvider(provider))
        .build();
  }

  @Bean
  public ElasticsearchTransport transport(RestClient client) {
    return new RestClientTransport(client, new JacksonJsonpMapper());
  }
}
