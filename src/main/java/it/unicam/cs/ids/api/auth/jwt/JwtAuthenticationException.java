package it.unicam.cs.ids.api.auth.jwt;

import org.springframework.security.core.AuthenticationException;


/**
 * Custom exception used to indicate authentication failures related to JWT processing.
 *
 * <p>This exception is typically thrown when:
 * <ul>
 *     <li>The JWT token is missing, malformed, or invalid.</li>
 *     <li>The token has expired or cannot be verified.</li>
 *     <li>The authentication process using JWT fails for any other reason.</li>
 * </ul>
 *
 * <p>Extends {@link AuthenticationException}, so it integrates seamlessly with Spring Security's
 * authentication exception handling mechanisms.
 *
 */
public class JwtAuthenticationException extends AuthenticationException {
    /**
     * Constructs a new {@code JwtAuthenticationException} with the specified detail message.
     *
     * @param message the detail message
     */
    public JwtAuthenticationException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code JwtAuthenticationException} with the specified detail message and cause.
     *
     * @param message the detail message
     * @param cause the cause of the exception
     */
    public JwtAuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}