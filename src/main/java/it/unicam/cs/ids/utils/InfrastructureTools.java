package it.unicam.cs.ids.utils;

import it.unicam.cs.ids.exceptions.auth.AuthenticationException;
import lombok.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;

public final class InfrastructureTools {
    private InfrastructureTools() {}

    /**
     * Validates the provided password against the hashed password using the specified PasswordEncoder.
     *
     * @param passwordEncoder the PasswordEncoder to use for validation
     * @param password the plain text password to validate
     * @param hashedPassword the hashed password to compare against
     * @throws AuthenticationException if the password does not match the hashed password
     */
    public static void validatePassword(
            @NonNull PasswordEncoder passwordEncoder,
            @NonNull String password,
            @NonNull String hashedPassword
    ) {
        if (!passwordEncoder.matches(password, hashedPassword)) {
            throw new AuthenticationException(Messages.Auth.INVALID_PASSWORD);
        }
    }


}
