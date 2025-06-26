package com.felipemcassiano.Mercatura.infra;

public record CheckoutResponseDTO(String status, String message, String sessionId, String sessionUrl) {
}
