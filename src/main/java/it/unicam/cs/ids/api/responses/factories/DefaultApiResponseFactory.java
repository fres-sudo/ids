package it.unicam.cs.ids.api.responses.factories;
import it.unicam.cs.ids.api.responses.models.ApiResponse;
import it.unicam.cs.ids.api.responses.models.PaginatedApiResponse;
import it.unicam.cs.ids.api.responses.models.FieldError;
import org.springframework.stereotype.Component;


import java.util.List;

/**
 * Default implementation of the {@link ApiResponseFactory} interface.
 * This class provides methods to create various types of API responses.
 * @see ApiResponse
 * @see PaginatedApiResponse
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
    public <T> PaginatedApiResponse<T> createPaginatedResponse(
            String message,
            List<T> data,
            int page,
            int size,
            int totalPages
    ) {
        return PaginatedApiResponse.<T>builder()
                .success(true)
                .code(200)
                .message(message)
                .data((T) data)
                .page(page)
                .size(size)
                .hasNext(page < totalPages - 1)
                .hasPrevious(page > 0)
                .totalPages(totalPages)
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
    public ApiResponse<String> createErrorResponse(int code, String message) {
        return ApiResponse.<String>builder()
                .success(false)
                .code(code)
                .message("An error occurred")
                .data(message)
                .build();
    }
}
