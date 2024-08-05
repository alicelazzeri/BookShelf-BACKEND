package it.alicelazzeri.book_shelf_backend.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.alicelazzeri.book_shelf_backend.entities.User;
import it.alicelazzeri.book_shelf_backend.exceptions.BadRequestException;
import it.alicelazzeri.book_shelf_backend.payloads.auth.UserLoginRequestDTO;
import it.alicelazzeri.book_shelf_backend.payloads.auth.UserLoginResponseDTO;
import it.alicelazzeri.book_shelf_backend.payloads.auth.UserRegisterRequestDTO;
import it.alicelazzeri.book_shelf_backend.services.AuthService;
import it.alicelazzeri.book_shelf_backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin
@Tag(name = "Auth API", description = "Operations related to user authentication")
public class AuthController {
    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    @Operation(summary = "Register a new user", description = "Register a new user",
            responses = {
                    @ApiResponse(responseCode = "201", description = "User registered successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input data")
            })
    public ResponseEntity<User> registerUser(
            @Parameter(description = "User registration data")
            @RequestBody @Validated UserRegisterRequestDTO registerPayload,
            BindingResult validation) {

        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }
        User result = userService.saveUser(registerPayload);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PostMapping("/register/admin")
    @Operation(summary = "Register a new admin", description = "Register a new admin",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Admin registered successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input data")
            })
    public ResponseEntity<User> registerAdmin(
            @Parameter(description = "Admin registration data")
            @RequestBody @Validated UserRegisterRequestDTO registerPayload,
            BindingResult validation) {

        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }

        User result = userService.saveUserAdmin(registerPayload);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    @Operation(summary = "User login", description = "Authenticate user and generate token",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Login successful",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserLoginResponseDTO.class))),
                    @ApiResponse(responseCode = "401", description = "Invalid credentials")
            })
    public ResponseEntity<UserLoginResponseDTO> login(@Parameter(description = "User login data") @RequestBody UserLoginRequestDTO loginPayload) {
        UserLoginResponseDTO response = authService.authenticateUserAndGenerateToken(loginPayload);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
