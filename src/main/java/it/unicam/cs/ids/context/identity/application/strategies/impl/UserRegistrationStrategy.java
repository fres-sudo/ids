package it.unicam.cs.ids.context.identity.application.strategies.impl;

import it.unicam.cs.ids.context.identity.application.mappers.UserMapper;
import it.unicam.cs.ids.context.identity.application.strategies.RegistrationStrategy;
import it.unicam.cs.ids.context.identity.domain.model.PlatformRoles;
import it.unicam.cs.ids.context.identity.domain.model.User;
import it.unicam.cs.ids.context.identity.domain.repositories.UserRepository;
import it.unicam.cs.ids.context.identity.infrastructure.web.dtos.requests.RegisterUserRequest;
import it.unicam.cs.ids.shared.application.EmailValidatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Registration strategy for regular users.
 * Handles the registration logic specific to User entities.
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserRegistrationStrategy implements RegistrationStrategy<User, RegisterUserRequest> {
    
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final EmailValidatorService emailValidatorService;
    
    @Override
    public User register(RegisterUserRequest request) {
        validateRequest(request);
        
        User user = userMapper.fromRequest(request);
        user.setRole(PlatformRoles.BUYER); // Default role for regular users
        
        return userRepository.save(user);
    }
    
    @Override
    public boolean canHandle(Class<?> requestType) {
        return RegisterUserRequest.class.equals(requestType);
    }
    
    @Override
    public void validateRequest(RegisterUserRequest request) {
        // Basic null check - other validations handled by @Valid annotations
        if (request == null) {
            throw new IllegalArgumentException("Registration request cannot be null");
        }
        
        // Business validation - check email uniqueness
        emailValidatorService.validateEmailInUse(request.getEmail());
    }
    
    @Override
    public Class<User> getEntityType() {
        return User.class;
    }
    
    @Override
    public Class<RegisterUserRequest> getRequestType() {
        return RegisterUserRequest.class;
    }
}