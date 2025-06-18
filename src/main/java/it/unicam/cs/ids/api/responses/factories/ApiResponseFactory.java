package it.unicam.cs.ids.api.responses.factories;

import it.unicam.cs.ids.api.responses.models.ApiError;
import it.unicam.cs.ids.api.responses.models.ApiResponse;
import it.unicam.cs.ids.api.responses.models.FieldError;
import it.unicam.cs.ids.api.responses.models.PaginatedApiResponse;
import org.springframework.context.annotation.Bean;

import java.util.List;

/**
 * Factory interface for creating various types of API responses.
 * This interface defines methods to create success responses, paginated responses,
 * validation error responses & and general error responses.
 */
public interface ApiResponseFactory {

    /**
     * Creates a success response with the given message and data.
     *
     * @param message the message to include in the response
     * @param data    the data to include in the response
     * @param <T>     the type of the data
     * @return an ApiResponse containing the success status, code, message, and data
     */
    <T> ApiResponse<T> createSuccessResponse(String message, T data);

    /**
     * Creates a paginated response with the given parameters.
     *
     * @param message     the message to include in the response
     * @param data        the data to include in the response
     * @param page        the current page number
     * @param size        the size of each page
     * @param totalPages  the total number of pages available
     * @param <T>         the type of the data
     * @return a PaginatedApiResponse containing pagination details and data
     */
    <T> PaginatedApiResponse<T> createPaginatedResponse(
            String message,
            List<T> data,
            int page,
            int size,
            int totalPages
    );

    /**
     * Creates a validation error response with the given message and list of field errors.
     *
     * @param message the message to include in the response
     * @param errors  the list of field errors to include in the response
     * @return an ApiError containing the validation errors
     */
    ApiError createValidationErrorResponse(String message, List<FieldError> errors);

    /**
     * Creates a general error response with the given code and message.
     *
     * @param code    the error code to include in the response
     * @param message the message to include in the response
     * @return an ApiError containing the error details
     */
    ApiError createErrorResponse(int code, String message);
}
