package it.unicam.cs.ids.context.identity.application.services;

import it.unicam.cs.ids.context.company.domain.models.Company;
import it.unicam.cs.ids.context.identity.domain.model.User;
import it.unicam.cs.ids.context.identity.infrastructure.web.dtos.requests.*;
import it.unicam.cs.ids.shared.kernel.exceptions.auth.AuthenticationException;
import it.unicam.cs.ids.shared.kernel.exceptions.auth.NotFound;
import it.unicam.cs.ids.shared.kernel.exceptions.auth.NotUniqueEmail;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import it.unicam.cs.ids.context.identity.infrastructure.web.dtos.responses.AuthResponse;


/**
 * Interface defining operations for user authentication and registration.
 */
public interface AuthService {
    /**
     * Logs in a user with the provided credentials.
     *
     * @param loginRequest the login requests containing email and password
     * @return an AuthResponse containing the authentication token and user details
     * @throws AuthenticationException if authentication fails due to invalid credentials
     */
    AuthResponse login(LoginRequest loginRequest);

    /**
     * Registers a new user with the provided details.
     *
     * @param registerUserRequest the requests containing user registration details
     * @throws NotUniqueEmail if the email provided in the registration requests is already in use
     * */
    void registerUser(RegisterUserRequest registerUserRequest);

    /**
     * Registers a certifier with the provided details.
     *
     * @param registerUserRequest the requests containing certifier registration details
     * @throws NotUniqueEmail if the email provided in the registration requests is already in use
     */
    void registerCertifier(RegisterUserRequest registerUserRequest);

    /**
     * Registers a new company with the provided details.
     *
     * @param registerCompanyRequest the requests containing company registration details
     * @throws NotUniqueEmail if the email provided in the registration requests is already in use
     */
    void registerCompany(RegisterCompanyRequest registerCompanyRequest);

    /**
     * Registers a new animator with the provided details.
     *
     * @param registerAnimatorRequest the requests containing animator registration details
     * @throws NotUniqueEmail if the email provided in the registration requests is already in use
     */
    void registerAnimator(RegisterAnimatorRequest registerAnimatorRequest);

    /**
     * Retrieves the currently authenticated company.
     *
     * @return the authenticated Company entity
     * @throws AuthenticationException if the user is not authenticated
     * @throws NotFound if no company is not found in the current security context
     */
    Company getAuthenticatedCompany();

    /**
     * Retrieves the currently authenticated user.
     *
     * @return the authenticated User entity
     * @throws AuthenticationException if the user is not authenticated
     * @throws NotFound if no user is found in the current security context
     */
    User getAuthenticatedUser();

    /**
     * Registers an admin with the provided details.
     *
     * @param registerAdminRequest the request containing admin registration details
     * @throws NotUniqueEmail if the email provided in the registration request is already in use
     * @throws AuthenticationException if the admin by-pass password does not match
     */
    void registerAdmin(@RequestBody RegisterAdminRequest registerAdminRequest);
}
