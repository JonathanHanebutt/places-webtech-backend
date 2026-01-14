package com.example.demo.auth;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final AppUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthController(AppUserRepository userRepository,
                          PasswordEncoder passwordEncoder,
                          JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }



    // DTO für Register
    public static class RegisterRequest {
        public String username;
        public String email;
        public String password;
    }

    public static class LoginRequest {
        public String username;
        public String password;
    }



    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@RequestBody RegisterRequest request) {

        if (userRepository.existsByUsername(request.username)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists");
        }

        if (userRepository.existsByEmail(request.email)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists");
        }

        AppUser user = new AppUser();
        user.setUsername(request.username);
        user.setEmail(request.email);
        user.setPasswordHash(
                passwordEncoder.encode(request.password)
        );

        userRepository.save(user);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {

        AppUser user = userRepository.findByUsername(request.username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials"));

        boolean ok = passwordEncoder.matches(request.password, user.getPasswordHash());
        if (!ok) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        }

        String token = jwtService.generateToken(user.getUsername());
        return new AuthResponse(user.getUsername(), token);
    }


    public static class AuthResponse {
        public String username;
        public String token;

        public AuthResponse(String username, String token) {
            this.username = username;
            this.token = token;
        }
    }
}
