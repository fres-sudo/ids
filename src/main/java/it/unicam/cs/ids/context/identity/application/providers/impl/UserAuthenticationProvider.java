package it.unicam.cs.ids.context.identity.application.providers.impl;

import it.unicam.cs.ids.context.catalog.domain.model.ApprovalStatus;
import it.unicam.cs.ids.context.identity.application.providers.AuthenticationProvider;
import it.unicam.cs.ids.context.identity.domain.model.PlatformRoles;
import it.unicam.cs.ids.context.identity.domain.model.RequestType;
import it.unicam.cs.ids.context.identity.domain.model.User;
import it.unicam.cs.ids.context.identity.domain.repositories.RoleRequestRepository;
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
 * Handles authentication logic specific to users including approval status validation
 * for special roles like CERTIFIER and ANIMATOR.
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserAuthenticationProvider implements AuthenticationProvider<User> {
    
    private final UserRepository userRepository;
    private final RoleRequestRepository roleRequestRepository;
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
        
        // Check approval status for special roles
        validateRoleApprovalStatus(user);
        
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
    
    /**
     * Validates that users with special roles (CERTIFIER, ANIMATOR) have been approved by admin.
     * Throws AuthenticationException if the role requires approval but hasn't been approved.
     *
     * @param user the user to validate
     * @throws AuthenticationException if role approval is required but not granted
     */
    private void validateRoleApprovalStatus(User user) {
        PlatformRoles userRole = user.getRole();
        
        // Only check approval for roles that require it
        if (userRole == PlatformRoles.CERTIFIER || userRole == PlatformRoles.ANIMATOR) {
            RequestType requestType = getRequestTypeForRole(userRole);
            
            // Check if user has an approved request for this role
            boolean hasApprovedRequest = roleRequestRepository
                .findByRequestingUserAndRequestTypeAndStatus(user, requestType, ApprovalStatus.APPROVED)
                .isPresent();
            
            if (!hasApprovedRequest) {
                throw new AuthenticationException(
                    "Your " + userRole.getRole().toLowerCase() + " role request is pending admin approval. Please contact an administrator."
                );
            }
        }
    }
    
    /**
     * Maps platform roles to their corresponding request types.
     */
    private RequestType getRequestTypeForRole(PlatformRoles role) {
        return switch (role) {
            case CERTIFIER -> RequestType.CERTIFIER;
            case ANIMATOR -> RequestType.ANIMATOR;
            default -> throw new IllegalArgumentException("No request type mapping for role: " + role);
        };
    }
}