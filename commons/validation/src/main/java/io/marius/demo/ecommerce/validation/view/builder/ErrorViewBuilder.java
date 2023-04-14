package io.marius.demo.ecommerce.validation.view.builder;

import io.marius.demo.ecommerce.validation.view.ErrorView;

import java.util.List;

public class ErrorViewBuilder<T>{
    private String name;
    private List<T> details;

    public ErrorViewBuilder<T> setName(String name) {
        this.name = name;
        return this;
    }

    public ErrorViewBuilder<T> setDetails(List<T> details) {
        this.details = details;
        return this;
    }

    public ErrorView<T> createErrorView() {
        return new ErrorView<T>(name, details);
    }
}