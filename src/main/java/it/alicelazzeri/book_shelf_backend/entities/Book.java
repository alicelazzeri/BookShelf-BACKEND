package it.alicelazzeri.book_shelf_backend.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "books")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(setterPrefix = "with")
public class Book extends BaseEntity {

    @Column(nullable = false)
    private String bookTitle;

    @Column(nullable = false)
    private String bookAuthor;

    @Column(nullable = false)
    private long isbnCode;

    @Column(nullable = false)
    private LocalDate addingDate;

    @Column (nullable = false)
    private LocalDate deletingDate;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String bookPlot;

    @Column(nullable = false)
    private int completedReadings;
}
