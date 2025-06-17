package com.felipemcassiano.Mercatura.models.shoppingCart;

import com.felipemcassiano.Mercatura.models.product.ProductResponseDTO;

import java.util.List;


public class ShoppingCart {

    private Long userId;
    private List<ProductResponseDTO> products;

    public ShoppingCart(Long userId) {
        this.userId = userId;
    }


    public Long getUserId() {
        return userId;
    }

    public void addProduct(ProductResponseDTO product) {
        this.products.add(product);
    }
}
