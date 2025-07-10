package it.unicam.cs.ids.exceptions.auth;

/**
 * AuthenticationException is thrown when there is an issue with user authentication.
 * This could be due to invalid credentials, expired tokens, or other authentication-related errors.
 */
public class AuthenticationException extends RuntimeException {
    /**
     * Constructs a new AuthenticationException with the specified detail message.
     *
     * @param message the detail message
     */
    public AuthenticationException(String message) {
        super(message);
    }

    /**
     * Constructs a new AuthenticationException with the specified detail message and cause.
     *
     * @param message the detail message
     * @param cause the cause of the exception
     */
    public AuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new AuthenticationException with the specified cause.
     *
     * @param cause the cause of the exception
     */
    public AuthenticationException(Throwable cause) {
        super(cause);
    }
}
