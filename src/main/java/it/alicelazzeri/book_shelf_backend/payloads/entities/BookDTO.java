package it.alicelazzeri.book_shelf_backend.payloads.entities;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record BookDTO(
        @NotNull(message = "Book title is mandatory")
        @NotEmpty(message = "Book title cannot be empty")
        String bookTitle,
        @NotNull(message = "Book author is mandatory")
        @NotEmpty(message = "Book author cannot be empty")
        String bookAuthor,
        long isbnCode,
        LocalDate addingDate,
        LocalDate deletingDate,
        @NotNull(message = "Book plot is mandatory")
        @NotEmpty(message = "Book plot cannot be empty")
        String bookPlot,
        int completedReadings
) {
}
