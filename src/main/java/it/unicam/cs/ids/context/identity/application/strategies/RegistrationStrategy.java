package it.unicam.cs.ids.context.identity.application.strategies;

/**
 * Strategy interface for handling different types of user registrations.
 * Each implementation handles the registration logic for a specific entity type.
 *
 * @param <T> the entity type (User, Company, etc.)
 * @param <R> the registration request type
 */
public interface RegistrationStrategy<T, R> {
    
    /**
     * Registers a new entity based on the provided request.
     *
     * @param request the registration request containing entity details
     * @return the created entity
     */
    T register(R request);
    
    /**
     * Determines if this strategy can handle the given request type.
     *
     * @param requestType the class type of the registration request
     * @return true if this strategy can handle the request type
     */
    boolean canHandle(Class<?> requestType);
    
    /**
     * Validates the registration request before processing.
     *
     * @param request the registration request to validate
     * @throws IllegalArgumentException if the request is invalid
     */
    void validateRequest(R request);
    
    /**
     * Gets the entity type this strategy handles.
     *
     * @return the entity class type
     */
    Class<T> getEntityType();
    
    /**
     * Gets the request type this strategy handles.
     *
     * @return the request class type
     */
    Class<R> getRequestType();
}