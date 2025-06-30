package it.unicam.cs.ids.api.auth.dto;


/**
 * LoginRequest is a record that represents a request to log in.
 * @param username the username (email) of the user trying to log in
 * @param password the password of the user trying to log in
 */
public record LoginRequest(String username, String password) {}
