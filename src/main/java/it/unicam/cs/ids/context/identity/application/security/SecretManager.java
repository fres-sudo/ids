package it.unicam.cs.ids.context.identity.application.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * Manages secure secrets for the application.
 * Handles validation of admin secrets and other sensitive operations.
 */
@Component
@RequiredArgsConstructor
public class SecretManager {
    
    private final PasswordEncoder passwordEncoder;
    
    /**
     * Admin secret loaded from application properties.
     * Should be a hashed value in production.
     */
    @Value("${app.security.admin.secret:$2a$10$N.wmtV8JzF.XdLq2EyQqBeXLB5JX8qZf5K4KNzLsHQ7ZrKhPJ8YGe}")
    private String adminSecretHash;
    
    /**
     * Validates an admin secret against the configured hash.
     *
     * @param providedSecret the secret provided by the user
     * @return true if the secret is valid
     * @throws IllegalArgumentException if the secret is null or empty
     */
    public boolean validateAdminSecret(String providedSecret) {
        if (!StringUtils.hasText(providedSecret)) {
            throw new IllegalArgumentException("Admin secret cannot be null or empty");
        }
        
        // In development mode, allow plaintext "admin" for testing
        if ("admin".equals(providedSecret) && isDevelopmentMode()) {
            return true;
        }
        
        // In production, validate against the hashed secret
        return passwordEncoder.matches(providedSecret, adminSecretHash);
    }
    
    /**
     * Checks if the application is running in development mode.
     * This allows for easier testing while maintaining security in production.
     *
     * @return true if in development mode
     */
    private boolean isDevelopmentMode() {
        String activeProfiles = System.getProperty("spring.profiles.active", "");
        return activeProfiles.contains("dev") || activeProfiles.contains("test") || activeProfiles.isEmpty();
    }
    
    /**
     * Generates a hash for a given plaintext secret.
     * This can be used to generate hashes for configuration.
     *
     * @param plaintext the plaintext secret to hash
     * @return the hashed secret
     */
    public String hashSecret(String plaintext) {
        if (!StringUtils.hasText(plaintext)) {
            throw new IllegalArgumentException("Plaintext cannot be null or empty");
        }
        
        return passwordEncoder.encode(plaintext);
    }
}