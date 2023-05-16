package io.marius.demo.ecommerce.inventory.mapper;

import io.marius.demo.ecommerce.inventory.entity.File;
import io.marius.demo.ecommerce.inventory.entity.Product;
import io.marius.demo.ecommerce.inventory.entity.ProductFile;
import io.marius.demo.ecommerce.inventory.entity.ProductProperty;
import io.marius.demo.ecommerce.inventory.model.payload.ProductInput;
import io.marius.demo.ecommerce.inventory.model.payload.PropertyInput;
import io.marius.demo.ecommerce.inventory.model.view.FileView;
import io.marius.demo.ecommerce.inventory.model.view.ProductView;
import io.marius.demo.ecommerce.inventory.repository.ProductPropertyRepository;
import java.util.List;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class ProductMapper {
  @Autowired ProductPropertyRepository productPropertyRepository;

  public abstract ProductView toProductView(Product entity);

  @Mapping(target = "productCategory", ignore = true)
  @Mapping(target = "properties", expression = "java(addProperties(product, input))")
  public abstract Product toProductEntity(ProductInput input);

  @Mapping(target = "productCategory", ignore = true)
  @Mapping(target = "properties", expression = "java(updateProperties(entity, input))")
  public abstract void update(@MappingTarget Product entity, ProductInput input);

  public abstract ProductProperty toProductProperty(PropertyInput input);

  public abstract void update(@MappingTarget ProductProperty entity, PropertyInput input);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "product", source = "product")
  @Mapping(target = "file", source = "file")
  public abstract ProductFile toProductFile(Product product, File file);

  @Mapping(target = "fileName", source = "entity.file.fileName")
  @Mapping(target = "key", source = "entity.file.key")
  @Mapping(target = "s3Url", source = "entity.file.s3Url")
  public abstract FileView toFileView(ProductFile entity);

  protected List<ProductProperty> addProperties(Product entity, ProductInput input) {
    List<ProductProperty> properties = null;
    if (input.getProperties() != null) {
      properties =
          input.getProperties().stream()
              .map(this::toProductProperty)
              .peek(e -> e.setProduct(entity))
              .toList();
    }

    return properties;
  }

  protected List<ProductProperty> updateProperties(Product entity, ProductInput input) {
    List<ProductProperty> properties = null;
    if (input.getProperties() != null) {
      properties =
          input.getProperties().stream()
              .map(
                  e -> {
                    ProductProperty property = null;
                    if (e.getId() != null) {
                      property = productPropertyRepository.findById(e.getId()).orElse(null);

                      if (property != null) {
                        this.update(property, e);
                      }
                    }

                    if (property == null) {
                      property = toProductProperty(e);
                    }

                    property.setProduct(entity);

                    return property;
                  })
              .toList();
    }

    if (entity.getProperties() != null && properties != null) {
      entity.getProperties().clear();
      entity.getProperties().addAll(properties);
    } else if (entity.getProperties() != null) {
      entity.getProperties().clear();
    } else {
      entity.setProperties(null);
    }

    return entity.getProperties();
  }
}
