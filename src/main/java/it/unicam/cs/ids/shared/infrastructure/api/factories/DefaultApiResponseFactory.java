package it.unicam.cs.ids.shared.infrastructure.api.factories;

import it.unicam.cs.ids.shared.application.Messages;
import it.unicam.cs.ids.shared.infrastructure.api.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;

import java.util.List;

/**
 * Default implementation of the {@link ApiResponseFactory} interface.
 * This class provides methods to create various types of API responses.
 * @see ApiResponse
 *
 */
@Component
public class DefaultApiResponseFactory implements ApiResponseFactory {

    @Override
    public <T> ApiResponse<T> createSuccessResponse(String message, T data) {
        return ApiResponse.<T>builder()
                .success(true)
                .code(200)
                .message(message)
                .data(data)
                .build();
    }

    @Override
    public ApiResponse<String> createGenericErrorResponse(HttpStatus status, String message) {
        return ApiResponse.<String>builder()
                .success(false)
                .code(status.value())
                .message(Messages.Error.GENERIC_ERROR)
                .data(message)
                .build();
    }

    @Override
    public ApiResponse<List<FieldError>> createValidationErrorResponse(String message, List<FieldError> errors) {
        return ApiResponse.<List<FieldError>>builder()
                .success(false)
                .code(400)
                .message(message)
                .data(errors)
                .build();
    }

    @Override
    public ApiResponse<String> createErrorResponse(HttpServletRequest req, Exception ex, HttpStatus status) {
        return ApiResponse.<String>builder()
                .success(false)
                .code(status.value())
                .requestId(req.getRequestId())
                .path(req.getRequestURI())
                .message(Messages.Error.GENERIC_ERROR)
                .data(ex.getLocalizedMessage())
                .build();
    }
}
