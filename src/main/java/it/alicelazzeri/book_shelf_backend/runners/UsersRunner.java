package it.alicelazzeri.book_shelf_backend.runners;

import com.github.javafaker.Faker;
import it.alicelazzeri.book_shelf_backend.entities.enums.Role;
import it.alicelazzeri.book_shelf_backend.payloads.auth.UserRegisterRequestDTO;
import it.alicelazzeri.book_shelf_backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class UsersRunner implements CommandLineRunner {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder bcrypt;

    private Faker faker = new Faker();

    @Override
    public void run(String... args) throws Exception {
        createUsers();
    }

    private void createUsers() {
        for (int i = 0; i < 10; i++) {
            String firstName = generateValidFirstName();
            String lastName = generateValidLastName();
            String email = faker.internet().emailAddress();
            String password = bcrypt.encode(faker.internet().password());

            UserRegisterRequestDTO userDto = new UserRegisterRequestDTO(
                    firstName,
                    lastName,
                    email,
                    password,
                    Role.USER
            );

            try {
                userService.saveUser(userDto);
            } catch (Exception e){
                System.out.println("Error while saving user with email : " + email + ". " + e.getMessage());
            }
        }

        System.out.println("Users have been successfully added to the DB.");
    }

    private String generateValidFirstName() {
        String firstName;
        do {
            firstName = faker.name().firstName();
        } while (
                firstName.length() < 3 || firstName.length() > 30
        );
        return firstName;
    }

    private String generateValidLastName() {
        String lastName;
        do {
            lastName = faker.name().lastName();
        } while (
                lastName.length() < 3 || lastName.length() > 30
        );
        return lastName;
    }

}
