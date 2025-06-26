package com.felipemcassiano.Mercatura.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record AddItemDTO(@NotNull Long productId, @Positive Long quantity) {
}
