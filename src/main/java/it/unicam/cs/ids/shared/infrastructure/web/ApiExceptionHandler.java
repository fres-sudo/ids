package it.unicam.cs.ids.shared.infrastructure.web;

import it.unicam.cs.ids.shared.application.Messages;
import it.unicam.cs.ids.shared.infrastructure.web.factories.ApiResponseFactory;
import it.unicam.cs.ids.shared.kernel.exceptions.auth.JwtAuthenticationException;
import jakarta.servlet.http.HttpServletRequest;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataAccessException;

import it.unicam.cs.ids.shared.infrastructure.web.responses.ApiResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.security.SignatureException;
import java.util.List;

/**
 * Api exception handler for the application.
 * This class handles various exceptions and returns appropriate API responses.
 * @see ApiResponse
 */
@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
    final ApiResponseFactory apiResponseFactory;

    @Autowired
    public ApiExceptionHandler(ApiResponseFactory apiResponseFactory) {
        this.apiResponseFactory = apiResponseFactory;
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, @NotNull HttpHeaders headers, @NotNull HttpStatusCode status, @NotNull WebRequest request) {

        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();

        ApiResponse<List<FieldError>> response = apiResponseFactory.createValidationErrorResponse(
                Messages.Error.INVALID_REQUEST,
                ex.getBindingResult().getFieldErrors()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    /**
     * Handles authentication errors and returns a 401 Unauthorized response.
     * @param ex the exception that was thrown
     * @return a ResponseEntity containing the ApiResponse with error details
     */
    @ExceptionHandler({JwtAuthenticationException.class, SignatureException.class})
    public ResponseEntity<ApiResponse<String>> handleUnauthorized(HttpServletRequest req, Exception ex) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        ApiResponse<String> response = apiResponseFactory.createErrorResponse(req, ex, status);
        return new ResponseEntity<>(response, status);
    }

    // For authorization errors (403)
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<String>> handleForbidden(HttpServletRequest req, Exception ex) {
        HttpStatus status = HttpStatus.FORBIDDEN;
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
