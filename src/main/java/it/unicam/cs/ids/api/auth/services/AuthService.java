package it.unicam.cs.ids.api.auth.services;

import org.springframework.web.bind.annotation.RequestBody;

import it.unicam.cs.ids.api.auth.dto.AuthResponse;
import it.unicam.cs.ids.api.auth.dto.LoginRequest;
import it.unicam.cs.ids.api.auth.dto.RegisterCompanyRequest;
import it.unicam.cs.ids.api.auth.dto.RegisterUserRequest;
import it.unicam.cs.ids.entities.Company;
import it.unicam.cs.ids.exceptions.auth.AuthenticationException;
import it.unicam.cs.ids.exceptions.auth.NotFound;
import it.unicam.cs.ids.exceptions.auth.NotUniqueEmail;

/**
 * Interface defining operations for user authentication and registration.
 */
public interface AuthService {
    /**
     * Logs in a user with the provided credentials.
     *
     * @param loginRequest the login request containing email and password
     * @return an AuthResponse containing the authentication token and user details
     * @throws it.unicam.cs.ids.exceptions.auth.AuthenticationException if authentication fails due to invalid credentials
     */
    AuthResponse login(LoginRequest loginRequest);

    /**
     * Registers a new user with the provided details.
     *
     * @param registerUserRequest the request containing user registration details
     * @throws NotUniqueEmail if the email provided in the registration request is already in use
     * */
    void registerUser(@RequestBody RegisterUserRequest registerUserRequest);

    /**
     * Registers a new company with the provided details.
     *
     * @param registerCompanyRequest the request containing company registration details
     * @throws NotUniqueEmail if the email provided in the registration request is already in use
     */
    void registerCompany(@RequestBody RegisterCompanyRequest registerCompanyRequest);

    /**
     * Retrieves the currently authenticated company.
     *
     * @return the authenticated Company entity
     * @throws AuthenticationException if the user is not authenticated
     * @throws NotFound if no company is not found in the current security context
     */
    Company getAuthenticatedCompany();


}
