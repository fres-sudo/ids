package it.unicam.cs.ids.context.identity.infrastructure.security.jwt;


import com.fasterxml.jackson.databind.ObjectMapper;
import it.unicam.cs.ids.context.identity.infrastructure.security.user.UserDetailsServiceImpl;
import it.unicam.cs.ids.shared.infrastructure.web.factories.ApiResponseFactory;
import it.unicam.cs.ids.shared.infrastructure.web.factories.DefaultApiResponseFactory;
import it.unicam.cs.ids.shared.infrastructure.web.responses.ApiResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Filter that processes JWT authentication for incoming HTTP requests.
 *
 * <p>This filter extracts the JWT token from the requests, validates it, and sets the
 * authentication in the security context if the token is valid.</p>
 *
 * <p>It is typically used in conjunction with a JWT token provider and a user details service.</p>
 */
@Component
@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    /**
     * The Token Provider
     */
    private final JwtTokenProvider tokenProvider;
    /**
     * The user details
     */
    private final UserDetailsServiceImpl userDetailsService;
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain
    ) throws IOException {

        try {
            final String token = getJwtFromRequest(request);

            if (token != null && tokenProvider.validateToken(token)) {
                String username = tokenProvider.getUsernameFromToken(token);
                // Extract authorities from JWT token
                String authoritiesString = tokenProvider.getAuthoritiesFromToken(token);
                Collection<? extends GrantedAuthority> authorities =
                        Arrays.stream(authoritiesString.split(","))
                                .map(SimpleGrantedAuthority::new)
                                .collect(Collectors.toList());

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                username,
                                null,
                                authorities
                        );
                authentication.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

            filterChain.doFilter(request, response);
        } catch (Exception e) {
            // Handle the exception directly in the filter
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json");

            ApiResponseFactory apiResponseFactory = new DefaultApiResponseFactory();
            ApiResponse<String> apiResponse = apiResponseFactory.createErrorResponse(request, e, HttpStatus.UNAUTHORIZED);

            response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
        }
    }

    /**
     * Extracts the JWT token from the Authorization header of the HTTP requests.
     *
     * <p>This method looks for a header named {@code Authorization} and checks if it starts
     * with the prefix {@code "Bearer "}. If the header is present and properly formatted,
     * it returns the token part (i.e., the substring after "Bearer ").</p>
     *
     * @param request the incoming {@link HttpServletRequest} containing the Authorization header
     * @return the JWT token if present and correctly formatted; otherwise, {@code null}
     */
    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}