package io.marius.demo.ecommerce.inventory.controller;

import io.marius.demo.ecommerce.inventory.service.ClassifierService;
import io.marius.demo.ecommerce.persistence.api.model.view.ClassifierView;
import java.util.List;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("classifiers")
public class ClassifiersController {
  private final ClassifierService classifierService;

  public ClassifiersController(ClassifierService classifierService) {

    this.classifierService = classifierService;
  }

  @GetMapping("/properties")
  public List<ClassifierView> getProperties() {
    return classifierService.getProperties();
  }

  @GetMapping("/categories")
  public List<ClassifierView> getCategories() {
    return classifierService.getCategories();
  }
}
