package com.felipemcassiano.Mercatura.models.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserDTO(@Email @NotBlank String email, @NotBlank String password) {
}
