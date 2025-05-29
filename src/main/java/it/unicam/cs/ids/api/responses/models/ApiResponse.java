package it.unicam.cs.ids.api.responses.models;

import lombok.experimental.SuperBuilder;

/**
 * Generic class representing a standard API response.
 * It contains a message, success status, response code, and data.
 * It's the base class that can be extended for specific response types.
 * It's used across all the API responses to maintain a consistent structure.
 * @param <T> the type of the data in the response.
 */
@SuperBuilder(toBuilder = true)
public class ApiResponse<T> {
    /**
     * Message describing the response.
     */
    private String message;
    /**
     * Indicates if the response is successful.
     */
    private boolean success;
    /**
     * Response code indicating the status of the request.
     */
    private int code;
    /**
     * The data returned in the response.
     */
    private T data;
}

