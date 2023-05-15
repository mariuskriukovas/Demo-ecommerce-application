package io.marius.demo.ecommerce.inventory.config;

import io.marius.demo.ecommerce.inventory.objectconverter.PropertyInputConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

  @Override
  public void addFormatters(FormatterRegistry registry) {
    registry.addConverter(new PropertyInputConverter());
  }
}
