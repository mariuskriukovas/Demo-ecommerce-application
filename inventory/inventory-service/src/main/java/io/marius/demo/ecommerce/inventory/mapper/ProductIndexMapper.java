package io.marius.demo.ecommerce.inventory.mapper;

import io.marius.demo.ecommerce.inventory.api.entity.BaseProductFile;
import io.marius.demo.ecommerce.inventory.elastic.entity.ProductIndex;
import io.marius.demo.ecommerce.inventory.model.view.FileView;
import io.marius.demo.ecommerce.inventory.model.view.ProductView;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = ClassifierMapper.class)
public abstract class ProductIndexMapper {
  @Mapping(target = "productCategory", source = "category")
  @Mapping(target = "productFiles", source = "files")
  public abstract ProductView toProductView(ProductIndex entity);

  @Mapping(target = "fileName", source = "entity.fileMetadata.fileName")
  @Mapping(target = "key", source = "entity.fileMetadata.fileKey")
  @Mapping(target = "s3Url", source = "entity.fileMetadata.s3Url")
  public abstract FileView toFileView(BaseProductFile entity);
}
