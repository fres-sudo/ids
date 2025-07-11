package it.unicam.cs.ids.context.identity.infrastructure.security.jwt;

import it.unicam.cs.ids.shared.kernel.exceptions.auth.JwtAuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import javax.crypto.SecretKey;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * A utility class responsible for generating, validating, and parsing JSON Web Tokens (JWT).
 * <p>
 * This provider uses the HS256 algorithm and retrieves secret, expiration, and issuer
 * configuration from application properties.
 * </p>
 *
 * Expected properties in {@code application.properties}:
 * <ul>
 *     <li>{@code app.jwt.secret} — base64-encoded secret key</li>
 *     <li>{@code app.jwt.expiration-ms} — expiration time in milliseconds</li>
 *     <li>{@code app.jwt.issuer} — the name of the token issuer</li>
 * </ul>
 */
@Component
public class JwtTokenProvider {

    @Value("${app.jwt.secret}")
    private String jwtSecret;

    @Value("${app.jwt.expiration-ms}")
    private int jwtExpirationMs;

    @Value("${app.jwt.issuer}")
    private String jwtIssuer;

    /**
     * Returns the secret signing key decoded from Base64 and used for signing the JWT.
     * @return the {@link SecretKey} for signing tokens
     */
    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Generates a signed JWT token containing the username and authorities as claims.
     *
     * @param authentication the Spring Security {@link Authentication} object
     * @return a signed JWT token as a {@link String}
     */
    public String generateToken(Authentication authentication) {
        final String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        return Jwts.builder()
                .subject(authentication.getName())
                .claim("auth", authorities)
                .issuer(jwtIssuer)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(getSigningKey(), Jwts.SIG.HS256)
                .compact();
    }


    /**
     * Validates the provided JWT token by parsing and verifying its signature and claims.
     *
     * @param token the JWT token
     * @return {@code true} if the token is valid
     * @throws JwtAuthenticationException if the token is invalid, expired, or malformed
     */
    public boolean validateToken(String token) {
        try {
            parseToken(token);
            return true;
        } catch (ExpiredJwtException ex) {
            throw new JwtAuthenticationException("Expired JWT token", ex);
        } catch (UnsupportedJwtException ex) {
            throw new JwtAuthenticationException("Unsupported JWT token", ex);
        } catch (MalformedJwtException ex) {
            throw new JwtAuthenticationException("Malformed JWT token", ex);
        } catch (SignatureException ex) {
            throw new JwtAuthenticationException("Invalid JWT signature", ex);
        } catch (IllegalArgumentException ex) {
            throw new JwtAuthenticationException("JWT claims string is empty", ex);
        } catch (JwtException ex) {
            throw new JwtAuthenticationException("Invalid JWT token", ex);
        }
    }

    /**
     * Parses the JWT token and returns the signed claims.
     *
     * @param token the JWT token
     * @return the parsed {@link Jws} containing {@link Claims}
     */
    private Jws<Claims> parseToken(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token);
    }

    /**
     * Extracts the username (subject) from the given JWT token.
     *
     * @param token the JWT token
     * @return the username as a {@link String}
     */
    public String getUsernameFromToken(String token) {
        return parseToken(token).getPayload().getSubject();
    }

    /**
     * Extracts the comma-separated authority list from the JWT token's "auth" claim.
     *
     * @param token the JWT token
     * @return a string containing roles or authorities (e.g., "ROLE_USER,ROLE_ADMIN")
     */
    public String getAuthoritiesFromToken(String token) {
        return parseToken(token).getPayload().get("auth", String.class);
    }
}