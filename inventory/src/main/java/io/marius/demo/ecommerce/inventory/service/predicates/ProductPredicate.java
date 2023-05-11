package io.marius.demo.ecommerce.inventory.service.predicates;

import static io.marius.demo.ecommerce.inventory.utility.FieldUtility.isFieldValid;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import io.marius.demo.ecommerce.inventory.entity.QProduct;
import io.marius.demo.ecommerce.inventory.entity.QProductProperty;
import io.marius.demo.ecommerce.inventory.model.query.ProductFilter;
import io.marius.demo.ecommerce.inventory.model.query.PropertyFilter;
import org.springframework.stereotype.Component;

@Component
public class ProductPredicate {
  public Predicate buildProductFilteringPredicate(ProductFilter filter, QProduct product) {
    BooleanBuilder queryBuilder = new BooleanBuilder();

    if (isFieldValid(filter.getName())) {
      queryBuilder.and(product.name.like("%" + filter.getName() + "%"));
    }

    if (isFieldValid(filter.getDescription())) {
      queryBuilder.and(product.description.like("%" + filter.getDescription() + "%"));
    }

    if (isFieldValid(filter.getCategory())) {
      queryBuilder.and(product.productCategory.name.eq(filter.getCategory()));
    }

    if (filter.getPriceFrom() != null || filter.getPriceTo() != null) {
      queryBuilder.and(
          product.price.between(
              filter.getPriceFrom() == null ? Double.MIN_VALUE : filter.getPriceFrom(),
              filter.getPriceTo() == null ? Double.MAX_VALUE : filter.getPriceTo()));
    }

    return queryBuilder;
  }

  public Predicate buildPropertiesFilteringPredicate(
      ProductFilter filter, QProductProperty productProperty) {
    BooleanBuilder queryBuilder = new BooleanBuilder();

    for (PropertyFilter propertyFilter : filter.getProperties()) {

      if (isFieldValid(propertyFilter.getName())) {
        queryBuilder.and(productProperty.name.like("%" + propertyFilter.getName() + "%"));
      }

      if (isFieldValid(propertyFilter.getDescription())) {
        queryBuilder.and(
            productProperty.description.like("%" + propertyFilter.getDescription() + "%"));
      }
    }

    return queryBuilder;
  }
}
