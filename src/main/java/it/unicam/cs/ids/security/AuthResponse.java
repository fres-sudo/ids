package it.unicam.cs.ids.security;

/**
 * AuthResponse represents the response returned after a successful authentication.
 * It contains a JWT token that can be used for subsequent requests.
 * @param token the JWT token issued upon successful authentication
 */
public record AuthResponse(String token) {}