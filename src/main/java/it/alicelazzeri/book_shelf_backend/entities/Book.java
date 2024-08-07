package it.alicelazzeri.book_shelf_backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
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
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Book extends BaseEntity {

    @Column(nullable = false)
    private String bookTitle;

    @Column(nullable = false)
    private String bookAuthor;

    @Column(nullable = false)
    private long isbnCode;

    @Column(nullable = false)
    private LocalDate addingDate;

    @Column
    private LocalDate deletingDate;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String bookPlot;

    @Column(nullable = false)
    private int completedReadings;

    @Column(nullable = false)
    private String bookCoverUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;
}
