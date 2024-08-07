package it.alicelazzeri.book_shelf_backend.controllers;

import com.itextpdf.text.DocumentException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.alicelazzeri.book_shelf_backend.entities.Book;
import it.alicelazzeri.book_shelf_backend.exceptions.BadRequestException;
import it.alicelazzeri.book_shelf_backend.exceptions.NoContentException;
import it.alicelazzeri.book_shelf_backend.payloads.entities.BookDTO;
import it.alicelazzeri.book_shelf_backend.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@RestController
@RequestMapping("/api/books")
@CrossOrigin
@Tag(name = "Book API", description = "Operations related to books")
public class BookController {

    @Autowired
    private BookService bookService;

    // GET http://localhost:8080/api/books

    @GetMapping
    @Operation(summary = "Get all books", description = "Retrieve all books")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of books",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Page.class))),
            @ApiResponse(responseCode = "204", description = "No books found")
    })
    public ResponseEntity<Page<Book>> getAllBooks(Pageable pageable) {
        Page<Book> books = bookService.getAllBooks(pageable);
        if (books.isEmpty()) {
            throw new NoContentException("No books found");
        }
        return ResponseEntity.ok(books);
    }

    // GET http://localhost:8080/api/books/{id}

    @GetMapping("/{id}")
    @Operation(summary = "Get book by ID", description = "Retrieve a book by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved book",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class))),
            @ApiResponse(responseCode = "404", description = "Book not found")
    })
    public ResponseEntity<Book> getBookById(@Parameter(description = "ID of the book to be retrieved") @PathVariable long id) {
        Book book = bookService.getBookById(id);
        return ResponseEntity.ok(book);
    }

    // POST http://localhost:8080/api/books?userId={id}

    @PostMapping
    @Operation(summary = "Create a new book", description = "Create a new book")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Book created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<Book> saveBook(
            @Parameter(description = "Book data to be created")
            @RequestBody @Validated BookDTO bookPayload,
            BindingResult validation,
            @RequestParam("userId") Long userId) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }
        Book savedBook = bookService.saveBook(bookPayload, userId);
        return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
    }

    // PUT http://localhost:8080/api/books/{id}

    @PutMapping("/{id}")
    @Operation(summary = "Update a book", description = "Update an existing book")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book updated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Book not found")
    })
    public ResponseEntity<Book> updateBook(
            @Parameter(description = "ID of the book to be updated") @PathVariable long id,
            @Parameter(description = "Updated book data") @RequestBody @Validated BookDTO updatedBook,
            BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }
        Book updatedBookEntity = bookService.updateBook(id, updatedBook);
        return ResponseEntity.ok(updatedBookEntity);
    }

    // DELETE http://localhost:8080/api/books/{id}

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a book", description = "Delete a book by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Book deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Book not found")
    })
    public ResponseEntity<Void> deleteBook(@Parameter(description = "ID of the book to be deleted") @PathVariable long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    // PUT http://localhost:8080/api/books/{id}/cover

    @PutMapping("/{id}/cover")
    @Operation(summary = "Update a book cover", description = "Update the cover image of an existing book")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book cover updated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Book not found")
    })
    public ResponseEntity<Book> updateBookCover(
            @Parameter(description = "ID of the book to be updated") @PathVariable long id,
            @Parameter(description = "Book cover file to be uploaded") @RequestParam("bookCoverFile") MultipartFile bookCoverFile) throws IOException {
        Book updatedBookEntity = bookService.updateBookCover(id, bookCoverFile);
        return ResponseEntity.ok(updatedBookEntity);
    }

    // GET http://localhost:8080/api/books/generate-pdf?userId={userId}

    @GetMapping("/generate-pdf")
    @Operation(summary = "Generate PDF", description = "Generate a PDF of the user's book list")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "PDF generated successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<byte[]> generateUserBooksPDF(@RequestParam("userId") Long userId) throws DocumentException, IOException {
        ByteArrayOutputStream pdfOutput = bookService.generateUserBooksPDF(userId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "user_books.pdf");

        return ResponseEntity.ok().headers(headers).body(pdfOutput.toByteArray());
    }
}
