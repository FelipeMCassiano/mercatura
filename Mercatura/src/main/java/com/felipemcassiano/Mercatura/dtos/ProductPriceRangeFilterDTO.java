package com.felipemcassiano.Mercatura.dtos;

import jakarta.annotation.Nullable;

public record ProductPriceRangeFilterDTO(@Nullable Long min, @Nullable Long max) {
}
