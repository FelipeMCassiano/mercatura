package com.felipemcassiano.Mercatura.controllers;

import com.felipemcassiano.Mercatura.models.shoppingCart.ShoppingCartDTO;
import com.felipemcassiano.Mercatura.models.user.User;
import com.felipemcassiano.Mercatura.services.ShoppingCartService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("shopping-cart")
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;

    public ShoppingCartController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @GetMapping()
    public ResponseEntity<ShoppingCartDTO> get(@AuthenticationPrincipal User userDetails) {
        String userEmail = userDetails.getUsername();
        ShoppingCartDTO shoppingCart = shoppingCartService.getCartByUser(userEmail);
        if (shoppingCart.products().isEmpty()) {
            return ResponseEntity.noContent().build();

        }
        return ResponseEntity.ok(shoppingCart);
    }

    @PostMapping()
    public ResponseEntity<Void> post(@AuthenticationPrincipal User userDetails, @RequestBody Long productId) {
        String userEmail = userDetails.getUsername();
        shoppingCartService.addToCart(userEmail, productId);
        return ResponseEntity.ok().build();
    }

}
