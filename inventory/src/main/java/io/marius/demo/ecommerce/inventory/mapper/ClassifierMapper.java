package io.marius.demo.ecommerce.inventory.mapper;

import io.marius.demo.ecommerce.inventory.entity.ProductCategory;
import io.marius.demo.ecommerce.inventory.entity.ProductProperty;
import io.marius.demo.ecommerce.persistence.api.model.view.ClassifierView;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public abstract class ClassifierMapper {
  public abstract ClassifierView toClassifierView(ProductCategory entity);

  public abstract ClassifierView toClassifierView(ProductProperty entity);
}
