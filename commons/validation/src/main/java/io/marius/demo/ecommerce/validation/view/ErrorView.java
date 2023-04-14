package io.marius.demo.ecommerce.validation.view;

import java.util.List;

public class ErrorView<T> {
    private String name;
    private List<T> details;

    public ErrorView(String name, List<T> details) {
        this.name = name;
        this.details = details;
    }

    public String getName() {
        return name;
    }

    public List<T> getDetails() {
        return details;
    }


}