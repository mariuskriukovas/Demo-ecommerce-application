package io.marius.demo.ecommerce.inventory.objectconverter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.marius.demo.ecommerce.inventory.model.payload.PropertyInput;
import jakarta.validation.ValidationException;
import org.springframework.core.convert.converter.Converter;

public class PropertyInputConverter implements Converter<String, PropertyInput> {

  @Override
  public PropertyInput convert(String from) {
    ObjectMapper objectMapper = new ObjectMapper();

    try {
      return objectMapper.readValue(from, PropertyInput.class);
    } catch (JsonProcessingException e) {
      throw new ValidationException("Unable to deserialize properties field", e);
    }
  }
}
