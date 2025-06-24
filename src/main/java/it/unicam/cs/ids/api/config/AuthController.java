package it.unicam.cs.ids.api.config;

import it.unicam.cs.ids.api.config.data.AuthResponse;
import it.unicam.cs.ids.api.config.data.LoginDto;
import it.unicam.cs.ids.api.config.data.RegisterDto;
import it.unicam.cs.ids.entities.User;
import it.unicam.cs.ids.repositories.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(AuthenticationManager authenticationManager,
                          JwtTokenProvider tokenProvider,
                          UserRepository userRepository,
                          PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginDto loginDto) {
        System.out.println("Login attempt for email: " + loginDto.username());
        User user = userRepository.findByEmail(loginDto.username())
                .orElseThrow(() -> new RuntimeException("User not found"));

        System.out.println("Input password: " + loginDto.password());
        System.out.println("DB hashed password: " + user.getHashedPassword());
        System.out.println("Password matches: " + passwordEncoder.matches(loginDto.password(), user.getHashedPassword()));

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.username(),
                        loginDto.password()
                )
        );

        String token = tokenProvider.generateToken(authentication);

        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDto registerDto) {
        if (userRepository.existsByEmail(registerDto.email())) {
            return ResponseEntity.badRequest().body("Email already in use");
        }
        User user = new User();
        user.setName(registerDto.name());
        user.setSurname(registerDto.surname());
        user.setEmail(registerDto.email());
        user.setHashedPassword(passwordEncoder.encode(registerDto.password()));
        user.setEmailVerified(true);
        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully");
    }


}