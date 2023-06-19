package io.marius.demo.ecommerce.inventory.service;

import static io.marius.demo.ecommerce.inventory.utility.FieldUtility.isFieldValid;

import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.marius.demo.ecommerce.inventory.elastic.repository.ProductIndexRepository;
import io.marius.demo.ecommerce.inventory.entity.*;
import io.marius.demo.ecommerce.inventory.mapper.ProductIndexMapper;
import io.marius.demo.ecommerce.inventory.mapper.ProductMapper;
import io.marius.demo.ecommerce.inventory.model.payload.BaseProductPayload;
import io.marius.demo.ecommerce.inventory.model.payload.ProductCreationPayload;
import io.marius.demo.ecommerce.inventory.model.query.ProductFilter;
import io.marius.demo.ecommerce.inventory.model.view.ProductView;
import io.marius.demo.ecommerce.inventory.repository.ProductCategoryRepository;
import io.marius.demo.ecommerce.inventory.repository.ProductRepository;
import io.marius.demo.ecommerce.inventory.service.predicates.ProductPredicate;
import jakarta.persistence.EntityManager;
import jakarta.validation.ValidationException;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service("ProductService")
public class ProductService {
  private final ProductRepository productRepository;
  private final ProductCategoryRepository productCategoryRepository;
  private final JPAQueryFactory queryFactory;
  private final ProductMapper productMapper;
  private final ProductIndexMapper productIndexMapper;
  private final ProductPredicate productPredicate;
  private final ImageService imageService;
  private final ProductIndexRepository productIndexRepository;

  public ProductService(
      ProductRepository productRepository,
      ProductCategoryRepository productCategoryRepository,
      EntityManager entityManager,
      ProductMapper productMapper,
      ProductIndexMapper productIndexMapper,
      ProductPredicate productPredicate,
      ImageService imageService,
      ProductIndexRepository productIndexRepository) {
    this.productRepository = productRepository;
    this.productCategoryRepository = productCategoryRepository;
    this.queryFactory = new JPAQueryFactory(entityManager);
    this.productMapper = productMapper;
    this.productIndexMapper = productIndexMapper;
    this.productPredicate = productPredicate;
    this.imageService = imageService;
    this.productIndexRepository = productIndexRepository;
  }

  @Transactional
  public String createProduct(ProductCreationPayload payload) {
    Product product = productMapper.toProductEntity(payload);
    product.setProductCategory(findProductCategory(payload));
    product.setProductFiles(loadProductFiles(payload.getFiles(), product));

    product = productRepository.save(product);
    return String.format("Successfully saved product with id: %d", product.getId());
  }

  @Transactional
  public String updateProduct(Long id, BaseProductPayload payload) {
    Product product = findProductById(id);

    productMapper.update(product, payload);
    product.setProductCategory(findProductCategory(payload));

    product = productRepository.save(product);
    return String.format("Successfully updated product with id: %d", product.getId());
  }

  @Transactional
  public String uploadProductFiles(Long id, List<MultipartFile> files) {
    Product product = findProductById(id);
    List<ProductFile> productFiles = loadProductFiles(files, product);

    if (product.getProductFiles() == null) {
      product.setProductFiles(productFiles);
    } else if (productFiles != null) {
      product.getProductFiles().addAll(productFiles);
    }

    productRepository.save(product);
    return String.format("Successfully updated files for product with id: %d", id);
  }

  @Transactional(readOnly = true)
  public ProductView findProduct(Long id) {
    return productMapper.toProductView(findProductById(id));
  }

  @Transactional(readOnly = true)
  public List<ProductView> findAllProducts(ProductFilter filter) {
    QProduct product = QProduct.product;
    QProductProperty productProperty = QProductProperty.productProperty;

    Predicate productQuery = productPredicate.buildProductFilteringPredicate(filter, product);

    JPAQuery<Product> query = queryFactory.selectFrom(product).where(productQuery);

    if (isFieldValid(filter.getProperties())) {
      Predicate propertyQuery =
          productPredicate.buildPropertiesFilteringPredicate(filter, productProperty);

      query.innerJoin(product.properties, productProperty).on(propertyQuery);
    }

    return query.fetch().stream().map(productMapper::toProductView).collect(Collectors.toList());
  }

  public List<ProductView> findAllPublicProducts(String searchBox, Pageable pageable) {
    if (searchBox == null || searchBox.isBlank()) {
      return StreamSupport.stream(productIndexRepository.findAll(pageable).spliterator(), true)
          .map(productIndexMapper::toProductView)
          .collect(Collectors.toList());
    }

    return productIndexRepository.findAllBySearchBox(searchBox, pageable).stream()
        .map(productIndexMapper::toProductView)
        .collect(Collectors.toList());
  }

  private Product findProductById(Long id) {
    return productRepository
        .findById(id)
        .orElseThrow(() -> new ValidationException("Product not found !"));
  }

  private ProductCategory findProductCategory(BaseProductPayload payload) {
    return productCategoryRepository
        .findByName(payload.getProductCategory().getName())
        .orElseThrow(() -> new ValidationException("Product category not found"));
  }

  private List<ProductFile> loadProductFiles(List<MultipartFile> files, Product product) {
    if (isFieldValid(files)) {
      return files.stream()
          .map(
              (MultipartFile multipartFile) -> {
                try {
                  return imageService.saveFile(multipartFile);
                } catch (IOException e) {
                  throw new ValidationException("Error uploading images");
                }
              })
          .map(e -> productMapper.toProductFile(product, e))
          .collect(Collectors.toList());
    }
    return null;
  }
}
