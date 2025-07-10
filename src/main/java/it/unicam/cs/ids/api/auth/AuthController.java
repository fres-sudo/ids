package it.unicam.cs.ids.api.auth;


import it.unicam.cs.ids.api.auth.dto.RegisterCompanyRequest;
import it.unicam.cs.ids.api.auth.dto.AuthResponse;
import it.unicam.cs.ids.api.auth.dto.LoginRequest;
import it.unicam.cs.ids.api.auth.dto.RegisterUserRequest;
import it.unicam.cs.ids.api.auth.services.AuthService;
import it.unicam.cs.ids.utils.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    /** Authentication service for handling login and registration */
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }


    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
        AuthResponse response = authService.login(loginRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register/user")
    public ResponseEntity<?> registerUser(@RequestBody RegisterUserRequest request) {
        authService.registerUser(request);
        return ResponseEntity.ok(Messages.Success.USER_REGISTERED);
    }

    @PostMapping("register/certifier")
    public ResponseEntity<?> registerCertifier(@RequestBody RegisterUserRequest request) {
        authService.registerCertifier(request);
        return ResponseEntity.ok(Messages.Success.CERTIFIER_REGISTERED);
    }

    @PostMapping("/register/company")
    public ResponseEntity<?> registerCompany(@RequestBody RegisterCompanyRequest request) {
        authService.registerCompany(request);
        return ResponseEntity.ok(Messages.Success.COMPANY_REGISTERED);
    }


}
