package com.felipemcassiano.Mercatura.models.product;

public record ProductDTO(String name, Integer price, Long quantity, ProductCategory category) {
}
