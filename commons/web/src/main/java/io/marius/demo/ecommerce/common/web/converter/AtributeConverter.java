package io.marius.demo.ecommerce.common.web.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ValidationException;
import org.springframework.core.convert.converter.Converter;

public abstract class AtributeConverter<T> implements Converter<String, T> {

  private final Class<T> type;

  public AtributeConverter(Class<T> type) {
    this.type = type;
  }

  @Override
  public T convert(String from) {
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      return objectMapper.readValue(from, type);
    } catch (JsonProcessingException e) {
      throw new ValidationException("Unable to deserialize properties field", e);
    }
  }
}
