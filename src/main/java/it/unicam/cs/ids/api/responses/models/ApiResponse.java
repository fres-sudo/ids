package it.unicam.cs.ids.api.responses.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * Generic class representing a standard API response.
 * It contains a message, success status, response code, and data.
 * It's the base class that can be extended for specific response types.
 * It's used across all the API responses to maintain a consistent structure.
 * @param <T> the type of the data in the response.
 */
@Getter // <--- Add this
@NoArgsConstructor // <--- Add this for Jackson
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public class ApiResponse<T> implements Serializable {
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

