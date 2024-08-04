package it.alicelazzeri.book_shelf_backend.payloads.auth;

public record UserLoginResponseDTO(
        long id,
        String accessToken,
        String firstName,
        String lastName,
        String email
) {
}
