package com.felipemcassiano.Mercatura.models.product;

public record ProductDTO(String name, Long price, Long stock, ProductCategory category) {
}
