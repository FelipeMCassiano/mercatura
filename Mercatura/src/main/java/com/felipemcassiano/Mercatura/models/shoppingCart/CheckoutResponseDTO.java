package com.felipemcassiano.Mercatura.models.shoppingCart;

public record CheckoutResponseDTO(String status, String message, String sessionId, String sessionUrl) {
}
