package it.unicam.cs.ids.api.auth.jwt;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.FilterChain;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import it.unicam.cs.ids.api.auth.user.UserDetailsServiceImpl;
import jakarta.servlet.ServletException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import java.io.IOException;
import org.springframework.lang.NonNull;

/**
 * Filter that processes JWT authentication for incoming HTTP requests.
 *
 * <p>This filter extracts the JWT token from the request, validates it, and sets the
 * authentication in the security context if the token is valid.</p>
 *
 * <p>It is typically used in conjunction with a JWT token provider and a user details service.</p>
 */
@Component
@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    /** The Token Provider */
    private final JwtTokenProvider tokenProvider;
    /** The user details */
    private final UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        final String token = getJwtFromRequest(request);

        if (token != null && tokenProvider.validateToken(token)) {
            String username = tokenProvider.getUsernameFromToken(token);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );

            authentication.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request)
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    /**
     * Extracts the JWT token from the Authorization header of the HTTP request.
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