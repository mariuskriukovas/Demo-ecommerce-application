package io.marius.demo.ecommerce.inventory.controller;

import io.marius.demo.ecommerce.inventory.entity.Product;
import io.marius.demo.ecommerce.inventory.repository.ProductRepository;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("graphql")
public class GraphQLController {
    private final ProductRepository productRepository;

    public GraphQLController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @QueryMapping(value = "product")
    public Optional<Product> getProduct(@Argument(name = "id") Long id) {
        System.out.println("Querying Product");
        return productRepository.findById(id);
    }

}
