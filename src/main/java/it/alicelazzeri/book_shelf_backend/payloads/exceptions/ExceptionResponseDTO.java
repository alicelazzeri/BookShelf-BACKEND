package it.alicelazzeri.book_shelf_backend.payloads.exceptions;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public record ExceptionResponseDTO(
        String message,
        HttpStatus httpStatus,
        LocalDateTime createdAt
) {
}
