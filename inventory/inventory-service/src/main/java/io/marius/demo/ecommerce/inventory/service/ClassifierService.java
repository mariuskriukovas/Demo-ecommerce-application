package io.marius.demo.ecommerce.inventory.service;

import io.marius.demo.ecommerce.common.api.view.ClassifierView;
import io.marius.demo.ecommerce.inventory.mapper.ClassifierMapper;
import io.marius.demo.ecommerce.inventory.repository.ProductCategoryRepository;
import io.marius.demo.ecommerce.inventory.repository.ProductPropertyRepository;
import jakarta.validation.ValidationException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("ClassifierService")
public class ClassifierService {
  private final ProductCategoryRepository productCategoryRepository;
  private final ProductPropertyRepository productPropertyRepository;
  private final ClassifierMapper classifierMapper;
  private final String PROPERTIES_CLASSIFIER_CODE = "properties";
  private final String CATEGORIES_CLASSIFIER_CODE = "categories";

  public ClassifierService(
      ProductCategoryRepository productCategoryRepository,
      ProductPropertyRepository productPropertyRepository,
      ClassifierMapper classifierMapper) {
    this.productCategoryRepository = productCategoryRepository;
    this.productPropertyRepository = productPropertyRepository;
    this.classifierMapper = classifierMapper;
  }

  @Transactional(readOnly = true)
  public List<ClassifierView> getProperties() {
    return productPropertyRepository.findAll().stream()
        .map(classifierMapper::toClassifierView)
        .collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public List<ClassifierView> getCategories() {
    return productCategoryRepository.findAll().stream()
        .map(classifierMapper::toClassifierView)
        .collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public ClassifierView getClassifierValue(String classifier, String code) {
    switch (classifier) {
      case PROPERTIES_CLASSIFIER_CODE:
        return classifierMapper.toClassifierView(
            productPropertyRepository
                .findByName(code)
                .orElseThrow(() -> new ValidationException("Classifier not found !")));
      case CATEGORIES_CLASSIFIER_CODE:
        return classifierMapper.toClassifierView(
            productCategoryRepository
                .findByName(code)
                .orElseThrow(() -> new ValidationException("Classifier not found !")));
      default:
        throw new ValidationException("Classifier not found !");
    }
  }
}
