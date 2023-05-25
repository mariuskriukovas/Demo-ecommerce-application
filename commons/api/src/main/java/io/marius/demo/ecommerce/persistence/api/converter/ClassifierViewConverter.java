package io.marius.demo.ecommerce.persistence.api.converter;

import io.marius.demo.ecommerce.persistence.api.model.view.ClassifierView;

public class ClassifierViewConverter extends AtributeConverter<ClassifierView> {
    public ClassifierViewConverter() {
        super(ClassifierView.class);
    }
}
