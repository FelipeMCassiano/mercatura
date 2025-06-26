package com.felipemcassiano.Mercatura.dtos;

import com.felipemcassiano.Mercatura.models.product.ProductCategory;
import jakarta.annotation.Nullable;

public record ProductFilterDTO(@Nullable ProductPriceRangeFilterDTO priceRange,
                               @Nullable Long stock,
                               @Nullable ProductCategory category) {
}
