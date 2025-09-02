package it.unicam.cs.ids.context.identity.application.services;

import it.unicam.cs.ids.context.company.domain.models.Company;
import it.unicam.cs.ids.context.identity.application.factories.AuthenticationProviderFactory;
import it.unicam.cs.ids.context.identity.application.factories.RegistrationStrategyFactory;
import it.unicam.cs.ids.context.identity.application.providers.AuthenticationProvider;
import it.unicam.cs.ids.context.identity.application.strategies.RegistrationStrategy;
import it.unicam.cs.ids.context.identity.domain.model.User;
import it.unicam.cs.ids.context.identity.infrastructure.security.jwt.JwtTokenProvider;
import it.unicam.cs.ids.context.identity.infrastructure.web.dtos.requests.*;
import it.unicam.cs.ids.context.identity.infrastructure.web.dtos.responses.AuthResponse;
import it.unicam.cs.ids.shared.kernel.exceptions.auth.AuthenticationException;
import it.unicam.cs.ids.shared.kernel.exceptions.auth.NotFound;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Refactored AuthService implementation using Strategy and Factory patterns.
 * This implementation separates concerns and provides better extensibility and maintainability.
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthServiceImpl implements AuthService {
    
    // Factories for strategy selection
    private final RegistrationStrategyFactory registrationFactory;
    private final AuthenticationProviderFactory authProviderFactory;
    
    // Spring Security components
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;
    
    @Override
    public AuthResponse login(LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();
        // Get the appropriate authentication provider
        AuthenticationProvider<?> provider = authProviderFactory.getProvider(email);

        // Authenticate the entity
        Object authenticatedEntity = provider.authenticate(email, password);

        // Use Spring Security for additional validation and token generation
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );

        // Generate JWT token
        String token = tokenProvider.generateToken(authentication);

        return new AuthResponse(token);
            
    }
    
    @Override
    public void registerUser(RegisterUserRequest registerUserRequest) {
        RegistrationStrategy<User, RegisterUserRequest> strategy =
                registrationFactory.getStrategy(RegisterUserRequest.class);

       strategy.register(registerUserRequest);
    }
    
    @Override
    public void registerCertifier(RegisterUserRequest registerUserRequest) {
        RegistrationStrategy<User, RegisterUserRequest> strategy =
                registrationFactory.getStrategyByType("certifier", RegisterUserRequest.class);

        strategy.register(registerUserRequest);
    }

    @Override
    public void registerCompany(RegisterCompanyRequest registerCompanyRequest) {
        RegistrationStrategy<Company, RegisterCompanyRequest> strategy =
                registrationFactory.getStrategy(RegisterCompanyRequest.class);

        strategy.register(registerCompanyRequest);

    }
    
    @Override
    public void registerAnimator(RegisterAnimatorRequest registerAnimatorRequest) {
        RegistrationStrategy<User, RegisterAnimatorRequest> strategy =
                registrationFactory.getStrategy(RegisterAnimatorRequest.class);

        strategy.register(registerAnimatorRequest);
    }
    
    @Override
    public void registerAdmin(RegisterAdminRequest registerAdminRequest) {
        RegistrationStrategy<User, RegisterAdminRequest> strategy =
                registrationFactory.getStrategy(RegisterAdminRequest.class);

        User registeredAdmin = strategy.register(registerAdminRequest);
            
    }
    
    @Override
    public Company getAuthenticatedCompany() {
        String email = getAuthenticatedEmail();
        
        AuthenticationProvider<?> provider = authProviderFactory.getProviderByEntityType("COMPANY");
        
        if (provider.supports(email)) {
            return (Company) provider.authenticate(email, null); // Password not needed for authenticated company
        }
        
        throw new NotFound("Company not found for email: " + email);
    }
    
    @Override
    public User getAuthenticatedUser() {
        String email = getAuthenticatedEmail();

        AuthenticationProvider<?> provider = authProviderFactory.getProviderByEntityType("USER");

        if (provider.supports(email)) {
            return (User) provider.authenticate(email, null); // Password not needed for authenticated user
        }

        throw new NotFound("User not found for email: " + email);
    }
    
    /**
     * Gets the email of the currently authenticated user from the security context.
     *
     * @return the authenticated user's email
     * @throws AuthenticationException if no user is authenticated
     */
    private String getAuthenticatedEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AuthenticationException("No authenticated user found");
        }
        
        return authentication.getName();
    }
}