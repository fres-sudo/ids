package it.unicam.cs.ids.context.identity.application.factories;

import it.unicam.cs.ids.context.identity.application.strategies.RegistrationStrategy;
import it.unicam.cs.ids.context.identity.application.strategies.impl.CertifierRegistrationStrategy;
import it.unicam.cs.ids.context.identity.infrastructure.web.dtos.requests.RegisterUserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Factory for selecting the appropriate registration strategy based on request type.
 * Uses the Strategy pattern to delegate registration logic to specific implementations.
 */
@Component
public class RegistrationStrategyFactory {
    
    private final List<RegistrationStrategy<?, ?>> strategies;
    private final CertifierRegistrationStrategy certifierRegistrationStrategy;
    private final Map<Class<?>, RegistrationStrategy<?, ?>> strategyMap;
    
    /**
     * Constructor that initializes the strategy map for quick lookup.
     */
    @Autowired
    public RegistrationStrategyFactory(List<RegistrationStrategy<?, ?>> strategies,
                                       CertifierRegistrationStrategy certifierRegistrationStrategy) {
        this.strategies = strategies;
        this.certifierRegistrationStrategy = certifierRegistrationStrategy;
        
        // Create a map for quick strategy lookup by request type
        this.strategyMap = strategies.stream()
                .filter(strategy -> !(strategy instanceof CertifierRegistrationStrategy))
                .collect(Collectors.toMap(
                        RegistrationStrategy::getRequestType,
                        Function.identity()
                ));
    }
    
    /**
     * Gets the appropriate strategy for the given request type.
     *
     * @param requestType the class type of the registration request
     * @param <T> the entity type
     * @param <R> the request type
     * @return the matching strategy
     * @throws UnsupportedOperationException if no strategy is found for the request type
     */
    @SuppressWarnings("unchecked")
    public <T, R> RegistrationStrategy<T, R> getStrategy(Class<R> requestType) {
        RegistrationStrategy<?, ?> strategy = strategyMap.get(requestType);
        
        if (strategy == null) {
            throw new UnsupportedOperationException("No registration strategy found for request type: " + requestType.getSimpleName());
        }
        
        return (RegistrationStrategy<T, R>) strategy;
    }
    
    /**
     * Gets the strategy for a specific registration type by name.
     * This is useful for special cases like certifier registration that uses the same request type as user registration.
     *
     * @param registrationType the type of registration (e.g., "user", "certifier", "admin")
     * @param requestType the class type of the registration request
     * @param <T> the entity type
     * @param <R> the request type
     * @return the matching strategy
     * @throws UnsupportedOperationException if no strategy is found
     */
    @SuppressWarnings("unchecked")
    public <T, R> RegistrationStrategy<T, R> getStrategyByType(String registrationType, Class<R> requestType) {
        if (registrationType.equalsIgnoreCase("certifier")) {
            if (RegisterUserRequest.class.equals(requestType)) {
                return (RegistrationStrategy<T, R>) certifierRegistrationStrategy;
            }
        } else {
            return getStrategy(requestType);
        }
        
        throw new UnsupportedOperationException(
                "No registration strategy found for type: " + registrationType + " with request type: " + requestType.getSimpleName()
        );
    }
    
    /**
     * Checks if a strategy exists for the given request type.
     *
     * @param requestType the class type to check
     * @return true if a strategy exists for the request type
     */
    public boolean hasStrategy(Class<?> requestType) {
        return strategyMap.containsKey(requestType);
    }
    
    /**
     * Gets all available processors.
     *
     * @return a list of all registration processors
     */
    public List<RegistrationStrategy<?, ?>> getAllStrategies() {
        return List.copyOf(strategies);
    }
}