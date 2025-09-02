package it.unicam.cs.ids.context.identity.application.strategies.impl;

import it.unicam.cs.ids.context.identity.application.mappers.UserMapper;
import it.unicam.cs.ids.context.identity.application.strategies.RegistrationStrategy;
import it.unicam.cs.ids.context.identity.domain.model.PlatformRoles;
import it.unicam.cs.ids.context.identity.domain.model.RequestType;
import it.unicam.cs.ids.context.identity.domain.model.RoleRequest;
import it.unicam.cs.ids.context.identity.domain.model.User;
import it.unicam.cs.ids.context.identity.domain.repositories.RoleRequestRepository;
import it.unicam.cs.ids.context.identity.domain.repositories.UserRepository;
import it.unicam.cs.ids.context.identity.infrastructure.web.dtos.requests.RegisterUserRequest;
import it.unicam.cs.ids.shared.application.EmailValidatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Registration strategy for certifier users.
 * Creates a basic user and a role request that needs admin approval.
 * User gets CERTIFIER role only after admin approval.
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CertifierRegistrationStrategy implements RegistrationStrategy<User, RegisterUserRequest> {
    
    private final UserRepository userRepository;
    private final RoleRequestRepository roleRequestRepository;
    private final UserMapper userMapper;
    private final EmailValidatorService emailValidatorService;
    
    @Override
    @Transactional
    public User register(RegisterUserRequest request) {
        validateRequest(request);
        
        // Create user with default BUYER role - will be upgraded after admin approval
        User user = userMapper.fromRequest(request);
        user.setRole(PlatformRoles.BUYER); // Default role until approved
        
        // Save user to get ID
        User savedUser = userRepository.save(user);
        
        // Create role request for admin approval
        RoleRequest roleRequest = new RoleRequest(savedUser, RequestType.CERTIFIER);
        roleRequestRepository.save(roleRequest);

        // TODO: Notify admin
        return savedUser;
    }
    
    @Override
    public boolean canHandle(Class<?> requestType) {
        // This strategy is used specifically for certifier registration
        // It will be selected by the factory based on context, not just request type
        return false; // Will be handled by explicit factory method
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