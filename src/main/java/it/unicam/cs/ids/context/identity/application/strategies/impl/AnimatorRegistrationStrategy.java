package it.unicam.cs.ids.context.identity.application.strategies.impl;

import it.unicam.cs.ids.context.identity.application.mappers.UserMapper;
import it.unicam.cs.ids.context.identity.application.strategies.RegistrationStrategy;
import it.unicam.cs.ids.context.identity.domain.model.PlatformRoles;
import it.unicam.cs.ids.context.identity.domain.model.User;
import it.unicam.cs.ids.context.identity.domain.repositories.UserRepository;
import it.unicam.cs.ids.context.identity.infrastructure.web.dtos.requests.RegisterAnimatorRequest;
import it.unicam.cs.ids.shared.application.EmailValidatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Registration strategy for animator users.
 * Handles the registration logic specific to Animator entities.
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AnimatorRegistrationStrategy implements RegistrationStrategy<User, RegisterAnimatorRequest> {
    
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final EmailValidatorService emailValidatorService;
    
    @Override
    public User register(RegisterAnimatorRequest request) {
        validateRequest(request);
        
        User animator = userMapper.fromRequest(request);
        animator.setRole(PlatformRoles.ANIMATOR);
        
        return userRepository.save(animator);
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