package it.unicam.cs.ids.api.responses.handlers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataAccessException;

import it.unicam.cs.ids.api.responses.models.ApiResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Global exception handler for the application.
 * This class handles various exceptions and returns appropriate API responses.
 * @see ApiResponse
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles validation errors and returns a 400 Bad Request response.
     * @param ex the exception that was thrown
     * @return a ResponseEntity containing the ApiResponse with error details
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponse<String>> handleConflict(DataIntegrityViolationException ex) {
        ApiResponse<String> response = ApiResponse.<String>builder()
                .message("Data integrity violation")
                .success(false)
                .code(HttpStatus.CONFLICT.value())
                .data(ex.getRootCause() != null ? ex.getRootCause().getMessage() : ex.getMessage())
                .build();
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    /**
     * Handles database-related errors and returns a 500 Internal Server Error response.
     * @param ex the exception that was thrown
     * @return a ResponseEntity containing the ApiResponse with error details
     */
    @ExceptionHandler({DataAccessException.class, java.sql.SQLException.class})
    public ResponseEntity<ApiResponse<String>> handleDatabaseError(Exception ex) {
        ApiResponse<String> response = ApiResponse.<String>builder()
                .message("Database error")
                .success(false)
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .data(ex.getMessage())
                .build();
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handles generic exceptions and returns a 500 Internal Server Error response.
     * @param req the HttpServletRequest that caused the exception
     * @param ex the exception that was thrown
     * @return a ResponseEntity containing the ApiResponse with error details
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<String>> handleGenericError(HttpServletRequest req, Exception ex) {
        ApiResponse<String> response = ApiResponse.<String>builder()
                .message("Unexpected error occurred")
                .success(false)
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .data(ex.getMessage())
                .build();
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
