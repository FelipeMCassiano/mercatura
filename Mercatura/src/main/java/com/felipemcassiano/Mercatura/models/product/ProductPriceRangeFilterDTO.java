package com.felipemcassiano.Mercatura.models.product;

import jakarta.annotation.Nullable;

public record ProductPriceRangeFilterDTO(@Nullable Integer min, @Nullable Integer max) {
}
