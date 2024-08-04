package it.alicelazzeri.book_shelf_backend.services;

import it.alicelazzeri.book_shelf_backend.entities.User;
import it.alicelazzeri.book_shelf_backend.exceptions.UnauthorizedException;
import it.alicelazzeri.book_shelf_backend.payloads.auth.UserLoginRequestDTO;
import it.alicelazzeri.book_shelf_backend.payloads.auth.UserLoginResponseDTO;
import it.alicelazzeri.book_shelf_backend.security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder bcrypt;

    @Autowired
    private JWTTools jwtTools;

    public UserLoginResponseDTO authenticateUserAndGenerateToken(UserLoginRequestDTO loginPayload) {
        User user = userService.findByEmail(loginPayload.email());
        if (bcrypt.matches(loginPayload.password(), user.getPassword())) {
            System.out.println("CORRECT password for created user: " + user.getEmail());
            String token = jwtTools.createToken(user);
            return new UserLoginResponseDTO(user.getId(), token, user.getFirstName(), user.getLastName(), user.getEmail());
        } else {
            System.out.println("INCORRECT password for created user: " + user.getEmail());
            throw new UnauthorizedException("Invalid credentials! Try login again.");
        }
    }
}

