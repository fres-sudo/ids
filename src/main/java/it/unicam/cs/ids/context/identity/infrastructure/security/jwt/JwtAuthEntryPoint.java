package it.unicam.cs.ids.context.identity.infrastructure.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.unicam.cs.ids.shared.infrastructure.web.factories.ApiResponseFactory;
import it.unicam.cs.ids.shared.infrastructure.web.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
 * and access relies on a valid token in the request's header.</p>
 *
 * @see org.springframework.security.web.AuthenticationEntryPoint
 */
@Component
public class JwtAuthEntryPoint implements AuthenticationEntryPoint {

    private final ApiResponseFactory apiResponseFactory;
    private final ObjectMapper objectMapper;

    @Autowired
    public JwtAuthEntryPoint(ApiResponseFactory apiResponseFactory, ObjectMapper objectMapper) {
        this.apiResponseFactory = apiResponseFactory;
        this.objectMapper = objectMapper;
    }

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException)
            throws IOException {

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        ApiResponse<String> apiResponse = apiResponseFactory.createErrorResponse(
                request, authException, HttpStatus.UNAUTHORIZED
        );

        response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
    }
}