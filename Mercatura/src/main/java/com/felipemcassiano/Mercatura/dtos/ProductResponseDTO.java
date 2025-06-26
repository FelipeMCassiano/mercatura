package com.felipemcassiano.Mercatura.dtos;

import com.felipemcassiano.Mercatura.models.product.ProductCategory;

import java.io.Serializable;

public record ProductResponseDTO(Long id, String name, Long price, Long stock,
                                 ProductCategory category) implements Serializable {
}
