package it.unicam.cs.ids.context.identity.application.providers;

/**
 * Authentication provider interface for handling different entity types.
 * Each implementation handles authentication logic for a specific entity type (User, Company, etc.).
 *
 * @param <T> the entity type this provider handles
 */
public interface AuthenticationProvider<T> {
    
    /**
     * Checks if this provider supports authentication for the given identifier.
     *
     * @param email the email identifier to check
     * @return true if this provider can authenticate the identifier
     */
    boolean supports(String email);
    
    /**
     * Authenticates an entity with the provided credentials.
     *
     * @param email the email identifier
     * @param password the password
     * @return the authenticated entity
     * @throws it.unicam.cs.ids.shared.kernel.exceptions.auth.AuthenticationException if authentication fails
     */
    T authenticate(String email, String password);
    
    /**
     * Gets the type of entity this provider handles.
     *
     * @return a string representing the entity type
     */
    String getEntityType();
    
    /**
     * Gets the entity class this provider handles.
     *
     * @return the entity class
     */
    Class<T> getEntityClass();
}