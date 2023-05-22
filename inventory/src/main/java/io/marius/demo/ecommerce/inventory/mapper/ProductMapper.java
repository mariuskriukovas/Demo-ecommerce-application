package io.marius.demo.ecommerce.inventory.mapper;

import io.marius.demo.ecommerce.inventory.entity.FileMetadata;
import io.marius.demo.ecommerce.inventory.entity.Product;
import io.marius.demo.ecommerce.inventory.entity.ProductFile;
import io.marius.demo.ecommerce.inventory.entity.ProductProperty;
import io.marius.demo.ecommerce.inventory.model.payload.BaseProductPayload;
import io.marius.demo.ecommerce.inventory.model.payload.ProductCreationPayload;
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
  @Mapping(target = "properties", expression = "java(addProperties(product, payload))")
  public abstract Product toProductEntity(ProductCreationPayload payload);

  @Mapping(target = "productCategory", ignore = true)
  @Mapping(target = "properties", expression = "java(updateProperties(entity, payload))")
  public abstract void update(@MappingTarget Product entity, BaseProductPayload payload);

  public abstract ProductProperty toProductProperty(PropertyInput input);

  public abstract void update(@MappingTarget ProductProperty entity, PropertyInput input);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "product", source = "product")
  @Mapping(target = "fileMetadata", source = "fileMetadata")
  public abstract ProductFile toProductFile(Product product, FileMetadata fileMetadata);

  @Mapping(target = "fileName", source = "entity.fileMetadata.fileName")
  @Mapping(target = "key", source = "entity.fileMetadata.key")
  @Mapping(target = "s3Url", source = "entity.fileMetadata.s3Url")
  public abstract FileView toFileView(ProductFile entity);

  protected List<ProductProperty> addProperties(Product entity, ProductCreationPayload payload) {
    List<ProductProperty> properties = null;
    if (payload.getProperties() != null) {
      properties =
          payload.getProperties().stream()
              .map(this::toProductProperty)
              .peek(e -> e.setProduct(entity))
              .toList();
    }

    return properties;
  }

  protected List<ProductProperty> updateProperties(Product entity, BaseProductPayload payload) {
    List<ProductProperty> properties = null;
    if (payload.getProperties() != null) {
      properties =
          payload.getProperties().stream()
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
