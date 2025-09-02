package it.unicam.cs.ids.context.identity.application.strategies.impl;

import it.unicam.cs.ids.context.identity.application.mappers.UserMapper;
import it.unicam.cs.ids.context.identity.application.security.SecretManager;
import it.unicam.cs.ids.context.identity.application.strategies.RegistrationStrategy;
import it.unicam.cs.ids.context.identity.domain.model.PlatformRoles;
import it.unicam.cs.ids.context.identity.domain.model.User;
import it.unicam.cs.ids.context.identity.domain.repositories.UserRepository;
import it.unicam.cs.ids.context.identity.infrastructure.web.dtos.requests.RegisterAdminRequest;
import it.unicam.cs.ids.shared.application.EmailValidatorService;
import it.unicam.cs.ids.shared.kernel.exceptions.auth.AuthenticationException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * Registration strategy for admin users.
 * Handles the registration logic specific to Admin entities with secret validation.
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AdminRegistrationStrategy implements RegistrationStrategy<User, RegisterAdminRequest> {
    
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final EmailValidatorService emailValidatorService;
    private final SecretManager secretManager;
    
    @Override
    public User register(RegisterAdminRequest request) {
        validateRequest(request);
        
        // Validate admin secret
        if (!secretManager.validateAdminSecret(request.getAdminSecret())) {
            throw new AuthenticationException("Invalid admin secret");
        }
        
        User admin = userMapper.fromRequest(request);
        admin.setRole(PlatformRoles.ADMIN);
        
        return userRepository.save(admin);
    }
    
    @Override
    public boolean canHandle(Class<?> requestType) {
        return RegisterAdminRequest.class.equals(requestType);
    }
    
    @Override
    public void validateRequest(RegisterAdminRequest request) {
        // Basic null check - other validations handled by @Valid annotations
        if (request == null) {
            throw new IllegalArgumentException("Registration request cannot be null");
        }
        
        if (!StringUtils.hasText(request.getAdminSecret())) {
            throw new IllegalArgumentException("Admin secret is required");
        }
        
        // Business validation - check email uniqueness
        emailValidatorService.validateEmailInUse(request.getEmail());
    }
    
    @Override
    public Class<User> getEntityType() {
        return User.class;
    }
    
    @Override
    public Class<RegisterAdminRequest> getRequestType() {
        return RegisterAdminRequest.class;
    }
}