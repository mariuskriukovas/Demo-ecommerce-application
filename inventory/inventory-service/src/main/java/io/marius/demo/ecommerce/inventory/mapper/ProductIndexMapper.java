package io.marius.demo.ecommerce.inventory.mapper;

import io.marius.demo.ecommerce.common.api.view.ClassifierView;
import io.marius.demo.ecommerce.inventory.elastic.entity.ProductIndex;
import io.marius.demo.ecommerce.inventory.model.view.ProductView;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = ClassifierMapper.class)
public abstract class ProductIndexMapper {
  @Mapping(target = "id", expression = "java(getId())")
  //  @Mapping(target = "uid", expression = "java(\"getUid()\")")
  @Mapping(target = "productCategory", expression = "java(getProductCategory())")
  public abstract ProductView toProductView(ProductIndex entity);

  protected String getUid() {
    return "1111-1111";
  }

  protected Long getId() {
    return 1L;
  }

  protected ClassifierView getProductCategory() {
    ClassifierView view = new ClassifierView();

    view.setId(1L);
    view.setName("lol");
    view.setDescription("lol");

    return view;
  }
}
