package it.unicam.cs.ids.api.auth;


import it.unicam.cs.ids.api.auth.dto.RegisterCompanyRequest;
import it.unicam.cs.ids.api.auth.dto.AuthResponse;
import it.unicam.cs.ids.api.auth.dto.LoginRequest;
import it.unicam.cs.ids.api.auth.dto.RegisterUserRequest;
import it.unicam.cs.ids.api.auth.services.AuthService;
import it.unicam.cs.ids.api.responses.factories.ApiResponseFactory;
import it.unicam.cs.ids.api.responses.models.ApiResponse;
import it.unicam.cs.ids.utils.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    /** Authentication service for handling login and registration */
    private final AuthService authService;
    private final ApiResponseFactory responseFactory;

    @Autowired
    public AuthController(AuthService authService, ApiResponseFactory responseFactory) {
        this.authService = authService;
        this.responseFactory = responseFactory;
    }


    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@RequestBody LoginRequest loginRequest) {
        AuthResponse authResponse = authService.login(loginRequest);
        ApiResponse<AuthResponse> response = responseFactory.createSuccessResponse(
                Messages.Success.USER_LOGGED_IN, authResponse
        );

        return ResponseEntity.ok(response);
    }

    @PostMapping("/register/user")
    public ResponseEntity<ApiResponse<?>> registerUser(@RequestBody RegisterUserRequest request) {
        authService.registerUser(request);
        ApiResponse<?> response = responseFactory.createSuccessResponse(
                Messages.Success.USER_REGISTERED,null
        );
        return ResponseEntity.ok(response);
    }

    @PostMapping("register/certifier")
    public ResponseEntity<ApiResponse<?>> registerCertifier(@RequestBody RegisterUserRequest request) {
        authService.registerCertifier(request);
        ApiResponse<?> response = responseFactory.createSuccessResponse(
                Messages.Success.CERTIFIER_REGISTERED,null
        );
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register/company")
    public ResponseEntity<ApiResponse<?>> registerCompany(@RequestBody RegisterCompanyRequest request) {
        authService.registerCompany(request);
        ApiResponse<?> response = responseFactory.createSuccessResponse(
                Messages.Success.COMPANY_REGISTERED,null
        );
        return ResponseEntity.ok(response);
    }
}
