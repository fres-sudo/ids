package it.unicam.cs.ids.context.identity.infrastructure.security.jwt;

import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;


/**
 * Entry point that is triggered when an unauthenticated user tries to access a secured HTTP endpoint.
 *
 * <p>This implementation is used by Spring Security to handle unauthorized access attempts.
 * It sends a 401 Unauthorized HTTP response when authentication fails or is missing.</p>
 *
 * <p>Typically used in JWT-based authentication setups where no login form is present,
 * and access relies on a valid token in the requests header.</p>
 *
 * @see org.springframework.security.web.AuthenticationEntryPoint
 */
@Component
public class JwtAuthEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException)
            throws IOException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
    }
}