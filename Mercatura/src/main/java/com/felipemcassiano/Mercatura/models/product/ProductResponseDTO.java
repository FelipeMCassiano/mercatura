package com.felipemcassiano.Mercatura.models.product;

import java.io.Serializable;

public record ProductResponseDTO(Long id, String name, Long price, Long stock,
                                 ProductCategory category) implements Serializable {
}
