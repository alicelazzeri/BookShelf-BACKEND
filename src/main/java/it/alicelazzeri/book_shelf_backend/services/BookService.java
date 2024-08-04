package it.alicelazzeri.book_shelf_backend.services;

import it.alicelazzeri.book_shelf_backend.entities.Book;
import it.alicelazzeri.book_shelf_backend.exceptions.NotFoundException;
import it.alicelazzeri.book_shelf_backend.payloads.entities.BookDTO;
import it.alicelazzeri.book_shelf_backend.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    // GET all books

    @Transactional(readOnly = true)
    public Page<Book> getAllBooks(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    // GET book by id

    @Transactional(readOnly = true)
    public Book getBookById(long id) {
        return bookRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Book with id: " + id + " not found."));
    }

    // POST saving book

    @Transactional
    public Book saveBook(BookDTO bookPayload) {
        Book book = mapToEntity(bookPayload);
        return bookRepository.save(book);
    }

    // PUT updating book
    @Transactional
    public Book updateBook(long id, BookDTO updatedBook) {
        Book bookToBeUpdated = this.getBookById(id);
        if (bookToBeUpdated == null) {
            throw new NotFoundException("Ingredient with id: " + id + " not found.");
        } else {
            updateBookFromDTO(bookToBeUpdated, updatedBook);
            return bookRepository.save(bookToBeUpdated);
        }
    }

    // DELETE book

    @Transactional
    public void deleteBook(long id) {
        if (!bookRepository.existsById(id)) {
            throw new NotFoundException("Book with id: " + id + " not found.");
        } else {
            bookRepository.deleteById(id);
        }
    }

    // Map BookDTO to Book entity (converts BookDTO to a Book entity instance in order to save or
    // update data on db via BookRepository)

    public Book mapToEntity(BookDTO bookDTO) {
        Book book = Book.builder()
                .withBookTitle(bookDTO.bookTitle())
                .withBookAuthor(bookDTO.bookAuthor())
                .withIsbnCode(bookDTO.isbnCode())
                .withAddingDate(bookDTO.addingDate())
                .withDeletingDate(bookDTO.deletingDate())
                .withBookPlot(bookDTO.bookPlot())
                .withCompletedReadings(bookDTO.completedReadings())
                .build();
        return book;
    }

    // update already existing book from BookDTO

    private void updateBookFromDTO(Book existingBook, BookDTO bookDTO) {
        existingBook.setBookTitle(bookDTO.bookTitle());
        existingBook.setBookAuthor(bookDTO.bookAuthor());
        existingBook.setIsbnCode(bookDTO.isbnCode());
        existingBook.setAddingDate(bookDTO.addingDate());
        existingBook.setDeletingDate(bookDTO.deletingDate());
        existingBook.setBookPlot(bookDTO.bookPlot());
        existingBook.setCompletedReadings(bookDTO.completedReadings());
    }

}
