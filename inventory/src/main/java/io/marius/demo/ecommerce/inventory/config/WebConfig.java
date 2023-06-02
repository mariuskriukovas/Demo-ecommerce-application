package io.marius.demo.ecommerce.inventory.config;

import io.marius.demo.ecommerce.common.api.converter.AtributeConverter;
import io.marius.demo.ecommerce.common.api.converter.ClassifierViewConverter;
import io.marius.demo.ecommerce.inventory.model.payload.PropertyInput;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

  @Override
  public void addFormatters(FormatterRegistry registry) {
    registry.addConverter(new ClassifierViewConverter());
    registry.addConverter(new PropertyInputConverter());
  }

  private class PropertyInputConverter extends AtributeConverter<PropertyInput> {
    public PropertyInputConverter() {
      super(PropertyInput.class);
    }
  }
}
