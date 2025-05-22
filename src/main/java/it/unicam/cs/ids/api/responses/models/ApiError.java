package it.unicam.cs.ids.api.responses.models;

import java.util.List;
import lombok.Data;
import lombok.experimental.SuperBuilder;

/**
 * Represents an error response from the API.
 * This class extends {@link ApiResponse} and includes a list of field errors.
 */
@SuperBuilder(toBuilder = true)
public class ApiError extends ApiResponse<Void> {

    private List<FieldError> errors;
}

