package it.unicam.cs.ids.shared.infrastructure.web.factories;

import it.unicam.cs.ids.shared.infrastructure.web.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;

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

    ApiResponse<String> createGenericErrorResponse(HttpStatus status, String message);

    /**
     * Creates a validation error response with the given message and list of field errors.
     *
     * @param message the message to include in the response
     * @param errors  the list of field errors to include in the response
     * @return an ApiError containing the validation errors
     */
    ApiResponse<List<FieldError>> createValidationErrorResponse(String message, List<FieldError> errors);

    /**
     * Creates am error response based on the exception thrown.
     *
     * @param req    the HttpServletRequest that caused the error
     * @param ex     the actual Exception that was thrown
     * @param status the HttpStatus of the response created
     * @return an ApiError containing the error details
     */
    ApiResponse<String> createErrorResponse(HttpServletRequest req, Exception ex, HttpStatus status);
}
