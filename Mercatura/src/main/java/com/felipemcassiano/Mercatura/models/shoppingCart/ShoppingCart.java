package com.felipemcassiano.Mercatura.models.shoppingCart;

import com.felipemcassiano.Mercatura.dtos.UserDTO;

import java.util.List;


public record ShoppingCart(List<UserDTO.CartProductDTO> products, Long total) {
}
