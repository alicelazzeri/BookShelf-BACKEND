package it.alicelazzeri.book_shelf_backend.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import it.alicelazzeri.book_shelf_backend.entities.Book;
import it.alicelazzeri.book_shelf_backend.entities.User;
import it.alicelazzeri.book_shelf_backend.exceptions.BadRequestException;
import it.alicelazzeri.book_shelf_backend.exceptions.NotFoundException;
import it.alicelazzeri.book_shelf_backend.payloads.entities.BookDTO;
import it.alicelazzeri.book_shelf_backend.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private Cloudinary cloudinary;

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

    @Transactional
    public Book updateBookCover(long id, MultipartFile bookCoverFile) throws IOException {
        Book bookToBeUpdated = this.getBookById(id);
        if (bookCoverFile != null && !bookCoverFile.isEmpty()) {
            Map<?, ?> uploadResult = cloudinary.uploader().upload(bookCoverFile.getBytes(), ObjectUtils.emptyMap());
            String imageUrl = (String) uploadResult.get("url");
            bookToBeUpdated.setBookCoverUrl(imageUrl);
        } else {
            throw new BadRequestException("Book cover file is empty or null.");
        }
        return bookRepository.save(bookToBeUpdated);
    }


    // Map BookDTO to Book entity (converts BookDTO to a Book entity instance in order to save or
    // update data on db via BookRepository)

    private Book mapToEntity(BookDTO bookDTO) {
        LocalDate addingDate = bookDTO.addingDate() != null ? bookDTO.addingDate() : LocalDate.now();
        LocalDate deletingDate = bookDTO.deletingDate();
        String bookCoverUrl = bookDTO.bookCoverUrl() != null ? bookDTO.bookCoverUrl() : "/images/unavailable.png";

        return Book.builder()
                .withBookTitle(bookDTO.bookTitle())
                .withBookAuthor(bookDTO.bookAuthor())
                .withIsbnCode(bookDTO.isbnCode())
                .withAddingDate(addingDate)
                .withDeletingDate(deletingDate)
                .withBookPlot(bookDTO.bookPlot())
                .withCompletedReadings(bookDTO.completedReadings())
                .withBookCoverUrl(bookCoverUrl)
                .build();
    }

    // update already existing book from BookDTO

    private void updateBookFromDTO(Book existingBook, BookDTO bookDTO) {
        existingBook.setBookTitle(bookDTO.bookTitle());
        existingBook.setBookAuthor(bookDTO.bookAuthor());
        existingBook.setIsbnCode(bookDTO.isbnCode());
        existingBook.setAddingDate(bookDTO.addingDate() != null ? bookDTO.addingDate() : existingBook.getAddingDate());
        existingBook.setDeletingDate(bookDTO.deletingDate());
        existingBook.setBookCoverUrl(bookDTO.bookCoverUrl() != null ? bookDTO.bookCoverUrl() : existingBook.getBookCoverUrl());
        existingBook.setBookPlot(bookDTO.bookPlot());
        existingBook.setCompletedReadings(bookDTO.completedReadings());
    }
}
