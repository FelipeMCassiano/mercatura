package com.felipemcassiano.Mercatura.dtos;

import com.felipemcassiano.Mercatura.models.product.ProductCategory;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

public record UserDTO(@Email @NotBlank String email, @NotBlank String password) {
    public static record CartProductDTO(Long id, String name, Long price, Long quantity,
                                        ProductCategory category) implements Serializable {
    }
}
