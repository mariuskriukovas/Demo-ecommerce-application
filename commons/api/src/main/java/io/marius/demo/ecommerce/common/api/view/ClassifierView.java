package io.marius.demo.ecommerce.common.api.view;

import io.marius.demo.ecommerce.common.api.BaseIdentifiable;

public class ClassifierView implements BaseIdentifiable {
  Long id;
  String uid;
  String name;
  String description;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @Override
  public String getUid() {
    return uid;
  }

  public void setUid(String uid) {
    this.uid = uid;
  }

  public static final class ClassifierViewBuilder {
    private Long id;
    private String uid;
    private String name;
    private String description;

    private ClassifierViewBuilder() {}

    public static ClassifierViewBuilder aClassifierView() {
      return new ClassifierViewBuilder();
    }

    public ClassifierViewBuilder withId(Long id) {
      this.id = id;
      return this;
    }

    public ClassifierViewBuilder withUid(String uid) {
      this.uid = uid;
      return this;
    }

    public ClassifierViewBuilder withName(String name) {
      this.name = name;
      return this;
    }

    public ClassifierViewBuilder withDescription(String description) {
      this.description = description;
      return this;
    }

    public ClassifierView build() {
      ClassifierView classifierView = new ClassifierView();
      classifierView.setId(id);
      classifierView.setUid(uid);
      classifierView.setName(name);
      classifierView.setDescription(description);
      return classifierView;
    }
  }
}
