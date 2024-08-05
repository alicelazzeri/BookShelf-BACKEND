package it.alicelazzeri.book_shelf_backend.services;

import it.alicelazzeri.book_shelf_backend.entities.Book;
import it.alicelazzeri.book_shelf_backend.entities.User;
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

    @Autowired
    private UserService userService;

    @Transactional(readOnly = true)
    public Page<Book> getAllBooks(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Book getBookById(long id) {
        return bookRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Book with id: " + id + " not found."));
    }

    @Transactional
    public Book saveBook(BookDTO bookPayload, long userId) {
        User user = userService.getUserById(userId).orElseThrow(
                () -> new NotFoundException("User with id: " + userId + " not found."));
        Book book = mapToEntity(bookPayload);
        book.setUser(user);
        return bookRepository.save(book);
    }

    @Transactional
    public Book updateBook(long id, BookDTO updatedBook) {
        Book bookToBeUpdated = this.getBookById(id);
        updateBookFromDTO(bookToBeUpdated, updatedBook);
        return bookRepository.save(bookToBeUpdated);
    }

    @Transactional
    public void deleteBook(long id) {
        if (!bookRepository.existsById(id)) {
            throw new NotFoundException("Book with id: " + id + " not found.");
        }
        bookRepository.deleteById(id);
    }

    // Map BookDTO to Book entity (converts BookDTO to a Book entity instance in order to save or
    // update data on db via BookRepository)

    private Book mapToEntity(BookDTO bookDTO) {
        return Book.builder()
                .withBookTitle(bookDTO.bookTitle())
                .withBookAuthor(bookDTO.bookAuthor())
                .withIsbnCode(bookDTO.isbnCode())
                .withAddingDate(bookDTO.addingDate())
                .withDeletingDate(bookDTO.deletingDate())
                .withBookPlot(bookDTO.bookPlot())
                .withCompletedReadings(bookDTO.completedReadings())
                .withBookCoverUrl(bookDTO.bookCoverUrl())
                .build();
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
        existingBook.setBookCoverUrl(bookDTO.bookCoverUrl());
    }
}
