package com.felipemcassiano.Mercatura.models.product;

public record ProductResponseDTO(Long id, String name, Long price, Long stock, ProductCategory category) {
}
