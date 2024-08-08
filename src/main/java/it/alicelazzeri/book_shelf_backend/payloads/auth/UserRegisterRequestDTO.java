package it.alicelazzeri.book_shelf_backend.payloads.auth;

import it.alicelazzeri.book_shelf_backend.entities.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserRegisterRequestDTO(
        @NotNull(message = "First name is mandatory")
        @NotEmpty(message = "First name cannot be empty")
        @Size(min = 3, max = 30, message = "First name should be between 3 and 30 characters")
        String firstName,

        @NotNull(message = "Last name is mandatory")
        @NotEmpty(message = "Last name cannot be empty")
        @Size(min = 3, max = 30, message = "Last name should be between 3 and 30 characters")
        String lastName,

        @NotEmpty (message = "Email cannot be empty")
        @Email(message = "User email must be a valid email address")
        String email,

        @NotNull(message = "User password is mandatory")
        @NotEmpty(message = "User password cannot be empty")
        @Size(min = 8, message = "User password must contain at least 8 characters")
        String password,
        Role role,
        String avatarUrl
) {
}
