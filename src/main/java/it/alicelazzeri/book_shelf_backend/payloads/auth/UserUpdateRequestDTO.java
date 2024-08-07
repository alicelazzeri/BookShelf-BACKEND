package it.alicelazzeri.book_shelf_backend.payloads.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record UserUpdateRequestDTO(
        @NotEmpty(message = "First name cannot be empty")
        @Size(min = 3, max = 30, message = "First name should be between 3 and 30 characters")
        String firstName,

        @NotEmpty(message = "Last name cannot be empty")
        @Size(min = 3, max = 30, message = "Last name should be between 3 and 30 characters")
        String lastName,

        @Email(message = "User email must be a valid email address")
        String email,

        @Size(min = 8, message = "User password must contain at least 8 characters")
        String password
) {
}
