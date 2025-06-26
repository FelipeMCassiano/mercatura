package com.felipemcassiano.Mercatura.controllers;

import com.felipemcassiano.Mercatura.dtos.AddItemDTO;
import com.felipemcassiano.Mercatura.infra.CheckoutResponseDTO;
import com.felipemcassiano.Mercatura.models.shoppingCart.ShoppingCart;
import com.felipemcassiano.Mercatura.models.user.User;
import com.felipemcassiano.Mercatura.services.ShoppingCartService;
import jakarta.validation.Valid;
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
    public ResponseEntity<ShoppingCart> get(@AuthenticationPrincipal User userDetails) {
        String userEmail = userDetails.getUsername();
        ShoppingCart shoppingCart = shoppingCartService.getCartByUser(userEmail);
        if (shoppingCart.products().isEmpty()) {
            return ResponseEntity.noContent().build();

        }
        return ResponseEntity.ok(shoppingCart);
    }

    @PostMapping()
    public ResponseEntity<Void> post(@AuthenticationPrincipal User userDetails, @RequestBody @Valid AddItemDTO request) {
        String userEmail = userDetails.getUsername();
        shoppingCartService.addToCart(userEmail, request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/checkout")
    public ResponseEntity<CheckoutResponseDTO> checkout(@AuthenticationPrincipal User userDetails) {
        String userEmail = userDetails.getUsername();
        CheckoutResponseDTO response = shoppingCartService.checkout(userEmail);

        return ResponseEntity.ok(response);
    }

}
