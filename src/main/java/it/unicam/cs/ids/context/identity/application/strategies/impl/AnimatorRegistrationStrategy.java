package it.unicam.cs.ids.context.identity.application.strategies.impl;

import it.unicam.cs.ids.context.identity.application.mappers.UserMapper;
import it.unicam.cs.ids.context.identity.application.strategies.RegistrationStrategy;
import it.unicam.cs.ids.context.identity.domain.model.PlatformRoles;
import it.unicam.cs.ids.context.identity.domain.model.RequestType;
import it.unicam.cs.ids.context.identity.domain.model.RoleRequest;
import it.unicam.cs.ids.context.identity.domain.model.User;
import it.unicam.cs.ids.context.identity.domain.repositories.RoleRequestRepository;
import it.unicam.cs.ids.context.identity.domain.repositories.UserRepository;
import it.unicam.cs.ids.context.identity.infrastructure.web.dtos.requests.RegisterAnimatorRequest;
import it.unicam.cs.ids.shared.application.EmailValidatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Registration strategy for animator users.
 * Creates a basic user and a role request that needs admin approval.
 * User gets ANIMATOR role only after admin approval.
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AnimatorRegistrationStrategy implements RegistrationStrategy<User, RegisterAnimatorRequest> {
    
    private final UserRepository userRepository;
    private final RoleRequestRepository roleRequestRepository;
    private final UserMapper userMapper;
    private final EmailValidatorService emailValidatorService;
    
    @Override
    @Transactional
    public User register(RegisterAnimatorRequest request) {
        validateRequest(request);
        
        // Create user with default BUYER role - will be upgraded after admin approval
        User user = userMapper.fromRequest(request);
        user.setRole(PlatformRoles.BUYER); // Default role until approved
        
        // Save user to get ID
        User savedUser = userRepository.save(user);

        // Create role request for admin approval
        RoleRequest roleRequest = new RoleRequest(savedUser, RequestType.ANIMATOR);
        roleRequestRepository.save(roleRequest);
        // TODO: Notify admin

        return savedUser;
    }
    
    @Override
    public boolean canHandle(Class<?> requestType) {
        return RegisterAnimatorRequest.class.equals(requestType);
    }
    
    @Override
    public void validateRequest(RegisterAnimatorRequest request) {
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
    public Class<RegisterAnimatorRequest> getRequestType() {
        return RegisterAnimatorRequest.class;
    }
}