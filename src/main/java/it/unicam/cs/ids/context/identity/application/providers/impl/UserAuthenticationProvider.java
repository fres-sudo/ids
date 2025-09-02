package it.unicam.cs.ids.context.identity.application.providers.impl;

import it.unicam.cs.ids.context.identity.application.providers.AuthenticationProvider;
import it.unicam.cs.ids.context.identity.domain.model.User;
import it.unicam.cs.ids.context.identity.domain.repositories.UserRepository;
import it.unicam.cs.ids.shared.application.Validator;
import it.unicam.cs.ids.shared.kernel.exceptions.auth.AuthenticationException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Authentication provider for User entities.
 * Handles authentication logic specific to users.
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserAuthenticationProvider implements AuthenticationProvider<User> {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    @Override
    public boolean supports(String email) {
        return userRepository.existsByEmail(email);
    }
    
    @Override
    public User authenticate(String email, String password) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        
        if (userOpt.isEmpty()) {
            throw new AuthenticationException("Invalid credentials");
        }
        
        User user = userOpt.get();
        
        // If password is provided, validate it. If null, assume already authenticated
        if (password != null) {
            Validator.validatePassword(passwordEncoder, password, user.getHashedPassword());
        }
        
        return user;
    }
    
    @Override
    public String getEntityType() {
        return "USER";
    }
    
    @Override
    public Class<User> getEntityClass() {
        return User.class;
    }
}