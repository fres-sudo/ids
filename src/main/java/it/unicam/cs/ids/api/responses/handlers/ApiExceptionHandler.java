package it.unicam.cs.ids.api.responses.handlers;

import it.unicam.cs.ids.api.responses.factories.ApiResponseFactory;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import it.unicam.cs.ids.api.responses.models.ApiResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

/**
 * Api exception handler for the application.
 * This class handles various exceptions and returns appropriate API responses.
 * @see ApiResponse
 */
@RestControllerAdvice
public class ApiExceptionHandler {
    final ApiResponseFactory apiResponseFactory;

    @Autowired
    public ApiExceptionHandler(ApiResponseFactory apiResponseFactory) {
        this.apiResponseFactory = apiResponseFactory;
    }
    /**
     * Handles authentication errors and returns a 401 Unauthorized response.
     * @param ex the exception that was thrown
     * @return a ResponseEntity containing the ApiResponse with error details
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<String>> handleUnauthorized(HttpServletRequest req, Exception ex) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        ApiResponse<String> response = apiResponseFactory.createErrorResponse(req, ex, status);
        return new ResponseEntity<>(response, status);
    }
    /**
     * Handles client errors and returns a 400 Bad Request response.
     * @param ex the exception that was thrown
     * @return a ResponseEntity containing the ApiResponse with error details
     */
    @ExceptionHandler({HttpClientErrorException.class, IllegalArgumentException.class})
    public ResponseEntity<ApiResponse<String>> handleClientError(HttpServletRequest req, Exception ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ApiResponse<String> response = apiResponseFactory.createErrorResponse(req, ex, status);
        return new ResponseEntity<>(response, status);
    }
    /**
     * Handles validation errors and returns a 400 Bad Request response.
     * @param ex the exception that was thrown
     * @return a ResponseEntity containing the ApiResponse with error details
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponse<String>> handleConflict(HttpServletRequest req, Exception ex) {
        HttpStatus status = HttpStatus.CONFLICT;
        ApiResponse<String> response = apiResponseFactory.createErrorResponse(req, ex, status);
        return new ResponseEntity<>(response, status);
    }

    /**
     * Handles database-related errors and returns a 500 Internal Server Error response.
     * @param ex the exception that was thrown
     * @return a ResponseEntity containing the ApiResponse with error details
     */
    @ExceptionHandler({DataAccessException.class, java.sql.SQLException.class})
    public ResponseEntity<ApiResponse<String>> handleDatabaseError(HttpServletRequest req, Exception ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ApiResponse<String> response = apiResponseFactory.createErrorResponse(req, ex, status);
        return new ResponseEntity<>(response, status);
    }

    /**
     * Handles generic exceptions and returns a 500 Internal Server Error response.
     * @param req the HttpServletRequest that caused the exception
     * @param ex the exception that was thrown
     * @return a ResponseEntity containing the ApiResponse with error details
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<String>> handleGenericError(HttpServletRequest req, Exception ex) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ApiResponse<String> response = apiResponseFactory.createErrorResponse(req, ex, status);
        return new ResponseEntity<>(response, status);
    }
}
