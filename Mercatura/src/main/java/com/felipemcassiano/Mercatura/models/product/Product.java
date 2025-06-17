package com.felipemcassiano.Mercatura.models.product;

import jakarta.persistence.*;

@Table
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    private Integer price;
    private Long quantity;
    private ProductCategory category;

    public Product() {
    }

    public Product(ProductDTO dto) {
        this.name = dto.name();
        this.price = dto.price();
        this.quantity = dto.quantity();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public ProductCategory getCategory() {
        return category;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
    }
}
