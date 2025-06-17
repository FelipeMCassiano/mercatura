package com.felipemcassiano.Mercatura.models.product;

import jakarta.annotation.Nullable;

public record ProductFilterDTO(@Nullable ProductPriceRangeFilterDTO priceRange,
                               @Nullable Integer quantity,
                               @Nullable ProductCategory category) {
}
