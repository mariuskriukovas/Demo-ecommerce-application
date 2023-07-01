package io.marius.demo.ecommerce.inventory.api.resource;

import io.marius.demo.ecommerce.common.api.view.ClassifierView;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface ClassifiersResource {
  @GetMapping("/properties")
  List<ClassifierView> getProperties();

  @GetMapping("/categories")
  List<ClassifierView> getCategories();

  @GetMapping("/{classifier}/{code}")
  ClassifierView getClassifierValue(@PathVariable String classifier, @PathVariable String code);
}
