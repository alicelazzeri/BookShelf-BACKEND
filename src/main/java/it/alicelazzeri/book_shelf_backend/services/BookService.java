package it.alicelazzeri.book_shelf_backend.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
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

import java.io.ByteArrayOutputStream;
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

    @Transactional(readOnly = true)
    public ByteArrayOutputStream generateBooksPDF(User user) throws DocumentException, IOException {
        Document document = new Document();
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, output);
        document.open();

        // Color configuration
        BaseColor black = new BaseColor(0, 0, 0);
        BaseColor bookColor = new BaseColor(212, 98, 64);
        BaseColor shelfColor = new BaseColor(21, 43, 60);
        BaseColor userColor = new BaseColor(212, 98, 64);

        // Font configuration
        BaseFont crimsonText = BaseFont.createFont("src/main/resources/fonts/CrimsonText/CrimsonText-Regular.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font crimsonTextFont = new Font(crimsonText, 12, Font.NORMAL, black);
        Font bookTitleFont = new Font(crimsonText, 12, Font.BOLDITALIC, black);

        BaseFont workSans = BaseFont.createFont("src/main/resources/fonts/WorkSans/WorkSans-Regular.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font workSansFont = new Font(workSans, 13, Font.NORMAL, black);
        Font userFont = new Font(workSans, 13, Font.BOLD, userColor);

        // Add logo with text
        Image logo = Image.getInstance("src/main/resources/static/images/logo.png");
        logo.scaleToFit(80, 80);
        logo.setAlignment(Element.ALIGN_CENTER);
        document.add(logo);

        Paragraph header = new Paragraph();
        header.add(new Chunk("Book", new Font(workSans, 20, Font.BOLD, bookColor)));
        header.add(new Chunk("Shelf", new Font(workSans, 20, Font.BOLD, shelfColor)));
        header.setAlignment(Element.ALIGN_CENTER);
        header.setSpacingAfter(20);
        document.add(header);

        // User information
        Paragraph userInfo = new Paragraph();
        userInfo.add(new Chunk("Books list of ", workSansFont));
        userInfo.add(new Chunk(user.getFirstName() + " " + user.getLastName(), userFont));
        userInfo.setAlignment(Element.ALIGN_CENTER);
        userInfo.setSpacingAfter(20);
        document.add(userInfo);

        // Books List
        for (Book book : user.getBooks()) {
            Paragraph bookInfo = new Paragraph();
            bookInfo.add(new Chunk("• " + book.getBookTitle(), bookTitleFont));
            bookInfo.add(new Chunk(" by " + book.getBookAuthor(), crimsonTextFont));
            bookInfo.add(new Chunk("\n" + book.getBookPlot(), crimsonTextFont));
            bookInfo.setAlignment(Element.ALIGN_LEFT);
            bookInfo.setSpacingAfter(15);
            document.add(bookInfo);
        }

        document.close();
        return output;
    }

    public ByteArrayOutputStream generateUserBooksPDF(Long userId) throws DocumentException, IOException {
        User user = userService.getUserById(userId).orElseThrow(() -> new NotFoundException("User not found"));
        return generateBooksPDF(user);
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
