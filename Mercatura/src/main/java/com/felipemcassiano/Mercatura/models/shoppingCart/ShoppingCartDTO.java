package com.felipemcassiano.Mercatura.models.shoppingCart;

import com.felipemcassiano.Mercatura.models.product.ProductResponseDTO;

import java.util.List;


public record ShoppingCartDTO(List<ProductResponseDTO> products) {
}
