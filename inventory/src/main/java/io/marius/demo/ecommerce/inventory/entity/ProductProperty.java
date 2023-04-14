package io.marius.demo.ecommerce.inventory.entity;

import io.marius.demo.ecommerce.persistence.entity.BaseEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "product_property")
public class ProductProperty extends BaseEntity {
    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "price", length = 1000)
    private String description;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
}