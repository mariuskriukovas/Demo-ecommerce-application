package io.marius.demo.ecommerce.inventory.service;

import static io.marius.demo.ecommerce.inventory.utility.FilterUtility.isValidFilter;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.marius.demo.ecommerce.inventory.entity.InventoryItem;
import io.marius.demo.ecommerce.inventory.entity.QInventoryItem;
import io.marius.demo.ecommerce.inventory.entity.QProduct;
import io.marius.demo.ecommerce.inventory.entity.QProductProperty;
import io.marius.demo.ecommerce.inventory.model.query.InventoryItemsFilter;
import io.marius.demo.ecommerce.inventory.repository.InventoryItemRepository;
import io.marius.demo.ecommerce.inventory.service.predicates.ProductPredicate;
import jakarta.persistence.EntityManager;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("InventoryService")
public class InventoryService {
  private final InventoryItemRepository inventoryItemRepository;
  private final ProductPredicate productPredicate;
  private final JPAQueryFactory queryFactory;

  public InventoryService(
      InventoryItemRepository inventoryItemRepository,
      ProductPredicate productPredicate,
      EntityManager entityManager) {
    this.inventoryItemRepository = inventoryItemRepository;
    this.productPredicate = productPredicate;
    this.queryFactory = new JPAQueryFactory(entityManager);
  }

  @Transactional(readOnly = true)
  public List<InventoryItem> findAllInventoryItems(InventoryItemsFilter filter) {
    QInventoryItem inventoryItem = QInventoryItem.inventoryItem;
    QProduct product = QProduct.product;
    QProductProperty productProperty = QProductProperty.productProperty;

    JPAQuery<InventoryItem> query =
        queryFactory
            .selectFrom(inventoryItem)
            .where(buildInventoryItemFilteringPredicate(filter, inventoryItem));

    if (filter.getProductFilter() != null) {
      query
          .innerJoin(inventoryItem.product, product)
          .on(productPredicate.buildProductFilteringPredicate(filter.getProductFilter(), product));

      if (isValidFilter(filter.getProductFilter().getProperties())) {
        query
            .innerJoin(product.properties, productProperty)
            .on(
                productPredicate.buildPropertiesFilteringPredicate(
                    filter.getProductFilter(), productProperty));
      }
    }

    return query.fetch();
  }

  private Predicate buildInventoryItemFilteringPredicate(
      InventoryItemsFilter filter, QInventoryItem inventoryItem) {
    BooleanBuilder queryBuilder = new BooleanBuilder();

    if (filter.getQuantityFrom() != null || filter.getQuantityTo() != null) {
      queryBuilder.and(
          inventoryItem.quantity.between(
              filter.getQuantityFrom() == null ? Integer.MIN_VALUE : filter.getQuantityFrom(),
              filter.getQuantityTo() == null ? Integer.MAX_VALUE : filter.getQuantityTo()));
    }

    return queryBuilder;
  }
}
