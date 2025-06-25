package com.felipemcassiano.Mercatura.models.product;

import jakarta.annotation.Nullable;

public record ProductPriceRangeFilterDTO(@Nullable Long min, @Nullable Long max) {
}
