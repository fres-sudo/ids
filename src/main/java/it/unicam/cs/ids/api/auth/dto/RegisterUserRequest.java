package it.unicam.cs.ids.api.auth.dto;

/**
 * Represents a request to register a new generic user.
 * @param name the real name of the user registering
 * @param surname the real surname of the user registering
 * @param email the username or email of the user registering
 * @param password the password of the user registering
 */
public record RegisterUserRequest(String name, String surname, String email, String password) {}
