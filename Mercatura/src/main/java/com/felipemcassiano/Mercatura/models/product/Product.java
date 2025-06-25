package com.felipemcassiano.Mercatura.models.product;

import jakarta.persistence.*;

@Table
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Long price;
    private Long stock;
    @Enumerated(EnumType.STRING)
    private ProductCategory category;

    public Product() {
    }

    public Product(ProductDTO dto) {
        this.name = dto.name();
        this.price = dto.price();
        this.stock = dto.stock();
        this.category = dto.category();
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

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getStock() {
        return stock;
    }

    public void setStock(Long quantity) {
        this.stock = quantity;
    }

    public ProductCategory getCategory() {
        return category;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
    }
}
