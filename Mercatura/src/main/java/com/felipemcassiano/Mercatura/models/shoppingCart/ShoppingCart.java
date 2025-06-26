package com.felipemcassiano.Mercatura.models.shoppingCart;

import java.util.List;


public record ShoppingCartDTO(List<CartProductDTO> products, Long total) {
}
