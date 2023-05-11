package io.marius.demo.ecommerce.inventory.mapper;

import io.marius.demo.ecommerce.inventory.entity.File;
import io.marius.demo.ecommerce.inventory.entity.Product;
import io.marius.demo.ecommerce.inventory.entity.ProductFile;
import io.marius.demo.ecommerce.inventory.entity.ProductProperty;
import io.marius.demo.ecommerce.inventory.model.payload.ProductInput;
import io.marius.demo.ecommerce.inventory.model.payload.PropertyInput;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public abstract class ProductMapper {
  @Mapping(target = "productCategory", ignore = true)
  public abstract Product toProductEntity(ProductInput input);

  @Mapping(target = "productCategory", ignore = true)
  @Mapping(
      target = "properties",
      nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  public abstract void update(@MappingTarget Product entity, ProductInput input);

  public abstract ProductProperty toProductProperty(PropertyInput input);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "product", source = "product")
  @Mapping(target = "file", source = "file")
  public abstract ProductFile toProductFile(Product product, File file);
}
