package it.unicam.cs.ids.context.identity.application.factories;

import it.unicam.cs.ids.context.identity.application.providers.AuthenticationProvider;
import it.unicam.cs.ids.shared.kernel.exceptions.auth.AuthenticationException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Factory for selecting the appropriate authentication provider based on the email identifier.
 * Uses the Strategy pattern to delegate authentication logic to specific implementations.
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthenticationProviderFactory {
    
    private final List<AuthenticationProvider<?>> authenticationProviders;
    
    /**
     * Gets the appropriate authentication provider for the given email identifier.
     *
     * @param email the email identifier to authenticate
     * @return the matching authentication provider
     * @throws AuthenticationException if no provider supports the email identifier
     */
    public AuthenticationProvider<?> getProvider(String email) {
        return authenticationProviders.stream()
                .filter(provider -> provider.supports(email))
                .findFirst()
                .orElseThrow(() -> new AuthenticationException("Invalid credentials"));
    }
    
    /**
     * Gets an authentication provider by entity type.
     *
     * @param entityType the entity type ("USER", "COMPANY")
     * @return the matching authentication provider
     * @throws AuthenticationException if no provider handles the entity type
     */
    public AuthenticationProvider<?> getProviderByEntityType(String entityType) {
        return authenticationProviders.stream()
                .filter(provider -> provider.getEntityType().equalsIgnoreCase(entityType))
                .findFirst()
                .orElseThrow(() -> new AuthenticationException("No authentication provider found for entity type: " + entityType));
    }
    
    /**
     * Checks if any provider supports the given email identifier.
     *
     * @param email the email identifier to check
     * @return true if a provider supports the email identifier
     */
    public boolean hasProvider(String email) {
        return authenticationProviders.stream()
                .anyMatch(provider -> provider.supports(email));
    }
    
    /**
     * Gets all available authentication providers.
     *
     * @return a list of all authentication providers
     */
    public List<AuthenticationProvider<?>> getAllProviders() {
        return List.copyOf(authenticationProviders);
    }
}