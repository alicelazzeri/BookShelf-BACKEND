package it.alicelazzeri.book_shelf_backend.services;

import it.alicelazzeri.book_shelf_backend.repositories.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder bcrypt;

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendRegistrationEmail(String email, String recipientName) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setTo(email);
            helper.setFrom("bookshelf.customerservice@gmail.com", "BookShelf");
            helper.setSubject("Welcome to BookShelf! 📚");
            String emailContent = "<html>" +
                    "<head>" +
                    "<style>" +
                    "@import url('https://fonts.googleapis.com/css2?family=Calistoga&display=swap');" +
                    "@import url('https://fonts.googleapis.com/css2?family=Raleway:ital,wght@0,100..900;1,100..900&display=swap');" +
                    "body { font-family: 'Raleway', Arial, sans-serif; font-weight: 400 }" +
                    ".email-container { padding: 20px; }" +
                    "</style>" +
                    "</head>" +
                    "<body>" +
                    "<div class=\"email-container\">" +
                    "<div>" +
                    "<p style=\"font-size: 18px;\">Hello <span style=\"color: green;\"><strong>" + recipientName + "</strong></span>, and welcome to 📚 <strong><span style=\"font-family: 'Calistoga', Arial, serif;\">BookShelf</span></strong>! 📚</p>" +
                    "<p style=\"font-size: 14px;\">We are delighted to have you join our community of book enthusiasts.</p>" +
                    "<p style=\"font-size: 14px;\">Your registration has been successful 🎉, and you are now part of a platform where you can manage your personal library, track your reading progress, and much more. ✨</p>" +
                    "<p style=\"font-size: 14px;\">With BookShelf, you can:</p>" +
                    "<ul>" +
                    "<li style=\"font-size: 14px;\">📚 <strong>Add, Edit, and Remove Books</strong>: Manage the books in your library with ease.</li>" +
                    "<li style=\"font-size: 14px;\">🔍 <strong>Track Reading Progress</strong>: Keep track of how many times you've read each book.</li>" +
                    "<li style=\"font-size: 14px;\">📅 <strong>View Book Details</strong>: See detailed information about each book, including title, author, ISBN, and more.</li>" +
                    "</ul>" +
                    "<p style=\"font-size: 14px;\">To get started, log in to your account and explore the features available to you. Remember, organizing your library has never been easier! 📖</p>" +
                    "<p style=\"font-size: 14px;\">❓ If you have any questions or need assistance, please don't hesitate to reach out to our support team via email at <a href='mailto:bookshelf.customerservice@gmail.com'>bookshelf.customerservice@gmail.com</a>.</p>" +
                    "<p style=\"font-size: 14px;\">Happy Reading! 💖</p>" +
                    "<p style=\"font-size: 14px;\">📚 The BookShelf Team 📚</p>" +
                    "</div>" +
                    "</div>" +
                    "</body>" +
                    "</html>";

            helper.setText(emailContent, true);
            javaMailSender.send(mimeMessage);

        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
