package it.unicam.cs.ids.context.identity.infrastructure.web;

import it.unicam.cs.ids.context.company.domain.models.CompanyRoles;
import it.unicam.cs.ids.context.identity.infrastructure.web.dtos.requests.RegisterAdminRequest;
import it.unicam.cs.ids.context.identity.infrastructure.web.dtos.requests.RegisterCompanyRequest;
import it.unicam.cs.ids.context.identity.infrastructure.web.dtos.responses.AuthResponse;
import it.unicam.cs.ids.context.identity.infrastructure.web.dtos.requests.LoginRequest;
import it.unicam.cs.ids.context.identity.infrastructure.web.dtos.requests.RegisterUserRequest;
import it.unicam.cs.ids.context.identity.application.services.AuthService;
import it.unicam.cs.ids.shared.infrastructure.web.factories.ApiResponseFactory;
import it.unicam.cs.ids.shared.infrastructure.web.responses.ApiResponse;
import it.unicam.cs.ids.shared.application.Messages;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for handling authentication-related operations such as login and registration.
 *
 * @see AuthService
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class AuthController {
    /** Authentication service for handling login and registration */
    private final AuthService authService;
    /** Factory for creating API responses */
    private final ApiResponseFactory responseFactory;

    /**
     * Endpoint for user login.
     * Accepts a {@link LoginRequest} containing email and password,
     * and returns an {@link AuthResponse} with the authentication token and user details.
     *
     * @param loginRequest the login request containing email and password
     * @return a response entity containing the authentication response
     */
    @PostMapping("/login")
    public ApiResponse<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
        AuthResponse authResponse = authService.login(loginRequest);
        return responseFactory.createSuccessResponse(
                Messages.Success.USER_LOGGED_IN, authResponse
        );
    }

    /**
     * Endpoint for user registration.
     * Accepts a {@link RegisterUserRequest} containing user details,
     * and registers the user in the system.
     *
     * @param request the registration request containing user details
     * @return a response entity indicating success
     */
    @PostMapping("/register/user")
    public ApiResponse<?> registerUser(@RequestBody RegisterUserRequest request) {
        authService.registerUser(request);
        return responseFactory.createSuccessResponse(
                Messages.Success.USER_REGISTERED,null
        );
    }

    /**
     * Endpoint for certifier registration.
     * Accepts a {@link RegisterUserRequest} containing certifier details,
     * and registers the certifier in the system.
     *
     * @param request the registration request containing certifier details
     * @return a response entity indicating success
     */
    @PostMapping("register/certifier")
    public ApiResponse<?> registerCertifier(@RequestBody RegisterUserRequest request) {
        authService.registerCertifier(request);
        return responseFactory.createSuccessResponse(
                Messages.Success.CERTIFIER_REGISTERED,null
        );
    }

    /**
     * Endpoint for admin registration.
     * Accepts a {@link RegisterUserRequest} containing admin details,
     * and registers the admin in the system.
     *
     * @param request the registration request containing admin details
     * @return a response entity indicating success
     */
    @PostMapping("/register/admin")
    public ApiResponse<?> registerAdmin(@RequestBody RegisterAdminRequest request) {
        authService.registerAdmin(request);
        return responseFactory.createSuccessResponse(
                Messages.Success.ADMIN_REGISTERED,null
        );
    }

    /**
     * Endpoint for company registration.
     * Accepts a {@link RegisterCompanyRequest} containing company details,
     * and registers the company in the system.
     *
     * @param request the registration request containing company details
     * @return a response entity indicating success
     */
    @PostMapping("/register/company/{role}")
    public ResponseEntity<ApiResponse<?>> registerCompany(
            @PathVariable("role") String role,
            @RequestBody RegisterCompanyRequest request
    ) {
        request.setRole(CompanyRoles.fromString(role));
        authService.registerCompany(request);

        ApiResponse<?> response = responseFactory.createSuccessResponse(
                Messages.Success.COMPANY_REGISTERED, null
        );
        return ResponseEntity.ok(response);
    }
}
