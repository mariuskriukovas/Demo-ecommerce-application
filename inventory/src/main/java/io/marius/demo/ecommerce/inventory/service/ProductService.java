package io.marius.demo.ecommerce.inventory.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.marius.demo.ecommerce.inventory.entity.Product;
import io.marius.demo.ecommerce.inventory.entity.QProduct;
import io.marius.demo.ecommerce.inventory.entity.QProductProperty;
import io.marius.demo.ecommerce.inventory.model.query.ProductFilter;
import io.marius.demo.ecommerce.inventory.model.query.PropertyFilter;
import io.marius.demo.ecommerce.inventory.repository.ProductRepository;
import io.marius.demo.ecommerce.inventory.repository.PropertyRepository;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("ProductService")
public class ProductService {
  private final ProductRepository productRepository;
  private final PropertyRepository propertyRepository;
  private final JPAQueryFactory queryFactory;

  public ProductService(
      ProductRepository productRepository,
      PropertyRepository propertyRepository,
      EntityManager entityManager) {
    this.productRepository = productRepository;
    this.propertyRepository = propertyRepository;
    this.queryFactory = new JPAQueryFactory(entityManager);
  }

  @Transactional(readOnly = true)
  public Optional<Product> findProduct(Long id) {
    return productRepository.findById(id);
  }

  @Transactional(readOnly = true)
  public List<Product> findAllProducts(ProductFilter filter) {
    QProduct product = QProduct.product;

    BooleanBuilder queryBuilder = new BooleanBuilder();

    if (isValidFilter(filter.getName())) {
      queryBuilder.and(product.name.like("%" + filter.getName() + "%"));
    }

    if (isValidFilter(filter.getDescription())) {
      queryBuilder.and(product.description.like("%" + filter.getDescription() + "%"));
    }

    if (isValidFilter(filter.getCategory())) {
      queryBuilder.and(product.productCategory.name.eq(filter.getCategory()));
    }

    if (filter.getPriceFrom() != null || filter.getPriceTo() != null) {
      queryBuilder.and(
          product.price.between(
              filter.getPriceFrom() == null ? Double.MIN_VALUE : filter.getPriceFrom(),
              filter.getPriceTo() == null ? Double.MAX_VALUE : filter.getPriceTo()));
    }

    if (!filter.getProperties().isEmpty()) {
      BooleanBuilder propertyQueryBuilder = new BooleanBuilder();
      QProductProperty productProperty = QProductProperty.productProperty;

      for (PropertyFilter propertyFilter : filter.getProperties()) {

        if (isValidFilter(propertyFilter.getName())) {
          propertyQueryBuilder.and(productProperty.name.like("%" + propertyFilter.getName() + "%"));
        }

        if (isValidFilter(propertyFilter.getDescription())) {
          propertyQueryBuilder.and(
              productProperty.description.like("%" + propertyFilter.getDescription() + "%"));
        }
      }

      return queryFactory
          .selectFrom(product)
          .innerJoin(product.properties, productProperty)
          .where(propertyQueryBuilder)
          .select(product)
          .where(queryBuilder)
          .fetch();
    }

    return StreamSupport.stream(productRepository.findAll(queryBuilder).spliterator(), false)
        .toList();
  }

  private boolean isValidFilter(String filter) {
    return filter != null && !filter.isEmpty() && !filter.trim().isEmpty();
  }
}
