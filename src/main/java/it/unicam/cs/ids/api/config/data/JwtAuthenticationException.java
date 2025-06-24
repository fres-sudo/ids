package it.unicam.cs.ids.api.config.data;

import org.springframework.security.core.AuthenticationException;

public class JwtAuthenticationException extends AuthenticationException {
    public JwtAuthenticationException(String message) {
        super(message);
    }
    public JwtAuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}