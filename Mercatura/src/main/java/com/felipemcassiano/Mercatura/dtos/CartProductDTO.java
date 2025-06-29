package com.felipemcassiano.Mercatura.dtos;

import com.felipemcassiano.Mercatura.models.product.ProductCategory;

import java.io.Serializable;

public record CartProductDTO(Long id, String name, Long price, Long quantity,
                             ProductCategory category) implements Serializable {
}
