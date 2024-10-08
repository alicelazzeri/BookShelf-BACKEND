package it.alicelazzeri.book_shelf_backend.services;

import it.alicelazzeri.book_shelf_backend.entities.User;
import it.alicelazzeri.book_shelf_backend.entities.enums.Role;
import it.alicelazzeri.book_shelf_backend.exceptions.BadRequestException;
import it.alicelazzeri.book_shelf_backend.exceptions.NotFoundException;
import it.alicelazzeri.book_shelf_backend.payloads.auth.UserRegisterRequestDTO;
import it.alicelazzeri.book_shelf_backend.payloads.auth.UserUpdateRequestDTO;
import it.alicelazzeri.book_shelf_backend.repositories.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

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
                    "@import url('https://fonts.googleapis.com/css2?family=Work+Sans:ital,wght@0,100..900;1,100..900&display=swap');" +
                    "@import url('https://fonts.googleapis.com/css2?family=Crimson+Text:ital,wght@0,400;0,600;0,700;1,400;1,600;1,700&display=swap');" +
                    "body { font-family: 'Crimson Text', Georgia, serif; font-weight: 400 }" +
                    ".email-container { padding: 20px; }" +
                    "</style>" +
                    "</head>" +
                    "<body>" +
                    "<div class=\"email-container\">" +
                    "<div>" +
                    "<p style=\"font-size: 16px;\">Hello <span style=\"color: #D46240;\">" + recipientName + "</span>, and welcome to 📚 <span style=\"font-family: 'Work Sans', Arial, serif;\"><span style=\"color: #D46240;\">Book</span><span style=\"color: #152B3C;\">Shelf</span></span>! 📚</p>" +
                    "<p style=\"font-size: 16px;\">We are delighted to have you join our community of book enthusiasts.</p>" +
                    "<p style=\"font-size: 16px;\">Your registration has been successful 🎉, and you are now part of a platform where you can manage your personal library, track your reading progress, and much more. ✨</p>" +
                    "<p style=\"font-size: 16px;\">With BookShelf, you can:</p>" +
                    "<ul>" +
                    "<li style=\"font-size: 16px;\">📚 <strong>Add, Edit, and Remove Books</strong>: Manage the books in your library with ease.</li>" +
                    "<li style=\"font-size: 16px;\">🔍 <strong>Track Reading Progress</strong>: Keep track of how many times you've read each book.</li>" +
                    "<li style=\"font-size: 16px;\">📅 <strong>View Book Details</strong>: See detailed information about each book, including title, author, ISBN, and more.</li>" +
                    "</ul>" +
                    "<p style=\"font-size: 16px;\">To get started, log in to your account and explore the features available to you. Remember, organizing your library has never been easier! 📖</p>" +
                    "<p style=\"font-size: 16px;\">❓ If you have any questions or need assistance, please don't hesitate to reach out to our support team via email at <a href='mailto:bookshelf.customerservice@gmail.com'>bookshelf.customerservice@gmail.com</a>.</p>" +
                    "<p style=\"font-size: 16px;\">Happy Reading! 💖</p>" +
                    "<p style=\"font-size: 16px;\">📚 The BookShelf Team 📚</p>" +
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

    private String generateAvatarUrl(String firstName, String lastName) {
        return "https://ui-avatars.com/api/?name=" + firstName + "+" + lastName + "&background=random";
    }

    // GET all users

    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // GET user by id

    @Transactional(readOnly = true)
    public Optional<User> getUserById(long id) {
        return userRepository.findById(id);
    }

    // POST saving user

    @Transactional
    public User saveUser(UserRegisterRequestDTO userPayload) {
        Optional<User> existingUser = userRepository.findByEmail(userPayload.email());
        if (existingUser.isPresent()) {
            throw new BadRequestException("User with email: " + userPayload.email() + " already exists.");
        }
        User user = new User();
        user.setFirstName(userPayload.firstName());
        user.setLastName(userPayload.lastName());
        user.setEmail(userPayload.email());
        user.setRole(userPayload.role() != null ? userPayload.role() : Role.USER);
        user.setPassword(bcrypt.encode(userPayload.password()));
        user.setAvatarUrl(userPayload.avatarUrl() != null && !userPayload.avatarUrl().isEmpty() ?
                userPayload.avatarUrl() : generateAvatarUrl(userPayload.firstName(), userPayload.lastName()));
        sendRegistrationEmail(userPayload.email(), userPayload.firstName());
        userRepository.save(user);
        return user;
    }

    // POST saving user with admin role

    @Transactional
    public User saveUserAdmin(UserRegisterRequestDTO userPayload){

        Optional<User> existingUser = userRepository.findByEmail(userPayload.email());
        if (existingUser.isPresent()) {
            throw new BadRequestException("User with email: " + userPayload.email() + " already exists.");
        }
        User user = new User();
        user.setFirstName(userPayload.firstName());
        user.setLastName(userPayload.lastName());
        user.setEmail(userPayload.email());
        user.setRole(Role.ADMIN);
        user.setPassword(bcrypt.encode(userPayload.password()));
        user.setAvatarUrl(userPayload.avatarUrl() != null && !userPayload.avatarUrl().isEmpty() ?
                userPayload.avatarUrl() : generateAvatarUrl(userPayload.firstName(), userPayload.lastName()));
        sendRegistrationEmail(userPayload.email(), userPayload.firstName());
        userRepository.save(user);
        return user;
    }

    // PUT updating existing user

    @Transactional
    public User updateUser(long id, UserUpdateRequestDTO userPayload) {
        Optional<User> userOptional = getUserById(id);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setFirstName(userPayload.firstName());
            user.setLastName(userPayload.lastName());

            if (userPayload.email() != null && !userPayload.email().isEmpty()) {
                user.setEmail(userPayload.email());
            }

            if (userPayload.password() != null && !userPayload.password().isEmpty()) {
                user.setPassword(bcrypt.encode(userPayload.password()));
            }

            return userRepository.save(user);
        } else {
            throw new NotFoundException("User with id: " + id + " not found.");
        }
    }

    // DELETE user by ID

    @Transactional
    public String deleteUser(long id) {
        Optional<User> userOptional = getUserById(id);

        if(userOptional.isPresent()){
            userRepository.deleteById(id);
            return "User with id: " + id + " deleted correctly.";
        }
        else{
            throw new NotFoundException("User with id: " + id + " not found.");
        }
    }

    // GET find user by email

    @Transactional(readOnly = true)
    public User findByEmail(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isPresent()){
            return userOptional.get();
        }
        else {
            throw new NotFoundException("User with email: " + email + " not found.");
        }
    }
}
