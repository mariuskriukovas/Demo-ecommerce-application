package io.marius.demo.ecommerce.inventory.service;

import static io.marius.demo.ecommerce.inventory.utility.FieldUtility.isFieldValid;

import com.amazonaws.services.s3.model.PutObjectResult;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.marius.demo.ecommerce.inventory.entity.*;
import io.marius.demo.ecommerce.inventory.entity.QProduct;
import io.marius.demo.ecommerce.inventory.entity.QProductProperty;
import io.marius.demo.ecommerce.inventory.mapper.ProductMapper;
import io.marius.demo.ecommerce.inventory.model.payload.ProductInput;
import io.marius.demo.ecommerce.inventory.model.query.ProductFilter;
import io.marius.demo.ecommerce.inventory.repository.ProductCategoryRepository;
import io.marius.demo.ecommerce.inventory.repository.ProductRepository;
import io.marius.demo.ecommerce.inventory.service.predicates.ProductPredicate;
import jakarta.persistence.EntityManager;
import jakarta.validation.ValidationException;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("ProductService")
public class ProductService {
  private final ProductRepository productRepository;
  private final ProductCategoryRepository productCategoryRepository;
  private final JPAQueryFactory queryFactory;
  private final ProductMapper productMapper;
  private final ProductPredicate productPredicate;
  private final FileService fileService;

  public ProductService(
          ProductRepository productRepository,
          ProductCategoryRepository productCategoryRepository,
          EntityManager entityManager,
          ProductMapper productMapper,
          ProductPredicate productPredicate, FileService fileService) {
    this.productRepository = productRepository;
    this.productCategoryRepository = productCategoryRepository;
    this.queryFactory = new JPAQueryFactory(entityManager);
    this.productMapper = productMapper;
    this.productPredicate = productPredicate;
    this.fileService = fileService;
  }

  @Transactional
  public String createProduct(ProductInput productInput) {

//    if (isFieldValid(productInput.getFiles())) {
//      try {
//        PutObjectResult result = fileService.saveFile(productInput.getFiles().get(0));
//        result.
//      } catch (IOException e) {
//        throw new ValidationException("Error uploading images");
//      }
//    }

    ProductCategory category =
        productCategoryRepository
            .findByName(productInput.getProductCategory())
            .orElseThrow(() -> new ValidationException("Product category not found"));

    Product product = null;

    if (productInput.getId() != null) {
      product = productRepository.findById(productInput.getId()).orElse(null);
      if (product != null) {
        productMapper.update(product, productInput);
      }
    }

    if (product == null) {
      product = productMapper.toProductEntity(productInput);
    }

    product.setProductCategory(category);

    if (product.getProperties() != null) {
      for (ProductProperty property : product.getProperties()) {
        property.setProduct(product);
      }
    }

    product = productRepository.save(product);
    return String.format("Successfully saved product with id: %d", product.getId());
  }

  @Transactional(readOnly = true)
  public Product findProduct(Long id) {
    return productRepository
        .findById(id)
        .orElseThrow(() -> new NoSuchElementException("Product not found"));
  }

  @Transactional(readOnly = true)
  public List<Product> findAllProducts(ProductFilter filter) {
    QProduct product = QProduct.product;
    QProductProperty productProperty = QProductProperty.productProperty;

    Predicate productQuery = productPredicate.buildProductFilteringPredicate(filter, product);

    JPAQuery<Product> query = queryFactory.selectFrom(product).where(productQuery);

    if (isFieldValid(filter.getProperties())) {
      Predicate propertyQuery =
          productPredicate.buildPropertiesFilteringPredicate(filter, productProperty);

      query.innerJoin(product.properties, productProperty).on(propertyQuery);
    }
    return query.fetch();
  }
}
