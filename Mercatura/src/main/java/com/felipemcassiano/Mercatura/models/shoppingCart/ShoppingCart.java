package com.felipemcassiano.Mercatura.models.shoppingCart;

import com.felipemcassiano.Mercatura.infra.CartProductDTO;

import java.util.List;


public record ShoppingCart(List<CartProductDTO> products, Long total) {
}
