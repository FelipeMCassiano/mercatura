package com.felipemcassiano.Mercatura.dtos;

import com.felipemcassiano.Mercatura.models.product.ProductCategory;

public record ProductDTO(String name, Long price, Long stock, ProductCategory category) {
}
