package com.felipemcassiano.Mercatura.dtos;

import java.util.List;

public record ApiResponseDTO<T>(List<T> items, long totalElements, int totalPages) {
}
