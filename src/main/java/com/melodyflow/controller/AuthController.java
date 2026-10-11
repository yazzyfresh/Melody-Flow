
package com.melodyflow.controller;

import com.melodyflow.dto.AuthResponse;
import com.melodyflow.dto.LoginRequest;
import com.melodyflow.dto.RegisterRequest;
import com.melodyflow.entity.User;
import com.melodyflow.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.melodyflow.repository.UserRoleRepository;



import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final UserRoleRepository userRoleRepository;


    public AuthController(
            UserService userService,
            PasswordEncoder passwordEncoder,
            UserRoleRepository userRoleRepository) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.userRoleRepository = userRoleRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @Valid @RequestBody RegisterRequest request) {
        try {
            User user = userService.register(request);

            Map<String, Object> response = Map.of(
                    "id", user.getId(),
                    "username", user.getUsername(),
                    "email", user.getEmail(),
                    "firstName", user.getFirstName(),
                    "lastName", user.getLastName()
            );

            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (IllegalArgumentException exception) {
            return ResponseEntity.badRequest().body(
                    Map.of("error", exception.getMessage())
            );
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @Valid @RequestBody LoginRequest request) {

        User user = userService.findByUsername(request.getUsername())
                .orElse(null);

        if (user == null ||
                !passwordEncoder.matches(
                        request.getPassword(),
                        user.getPassword())) {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    Map.of("error", "Invalid username or password")
            );
        }

        var userRoles = userRoleRepository.findByUserId(user.getId());

        String role = userRoles.isEmpty()
                ? "USER"
                : userRoles.get(0).getRole().getName();

        AuthResponse response = new AuthResponse(
                null,
                user.getUsername(),
                role
        );

        return ResponseEntity.ok(response);
    }
}
