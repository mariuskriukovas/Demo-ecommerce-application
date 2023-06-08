package io.marius.demo.ecommerce.elasticindexerservice.mapper;

import io.marius.demo.ecommerce.elasticindexerservice.elastic.document.PhoneIndex;
import io.marius.demo.ecommerce.elasticindexerservice.sheet.PhoneDataRow;
import java.util.UUID;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public abstract class ProductMapper {
  @Mapping(target = "uid", expression = "java(getUid())")
  public abstract PhoneIndex toPhoneIndex(PhoneDataRow phoneDataRow);

  protected String getUid() {
    return UUID.randomUUID().toString();
  }

  protected Boolean toBoolean(String str) {
    String lowerCaseStr = str.toLowerCase();

    return lowerCaseStr.contains("yes") || lowerCaseStr.contains("class");
  }
}
