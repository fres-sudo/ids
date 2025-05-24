package it.unicam.cs.ids.api.responses.factories;
import it.unicam.cs.ids.api.responses.models.ApiResponse;
import it.unicam.cs.ids.api.responses.models.PaginatedApiResponse;
import it.unicam.cs.ids.api.responses.models.ApiError;
import it.unicam.cs.ids.api.responses.models.FieldError;


import java.util.List;

/**
 * Default implementation of the {@link ApiResponseFactory} interface.
 * This class provides methods to create various types of API responses.
 * @see ApiResponse
 * @see PaginatedApiResponse
 * @see ApiError
 *
 */
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
            T data,
            int page,
            int size,
            int totalPages
    ) {
        return PaginatedApiResponse.<T>builder()
                .success(true)
                .code(200)
                .message(message)
                .data(data)
                .page(page)
                .size(size)
                .hasNext(page < totalPages - 1)
                .hasPrevious(page > 0)
                .totalPages(totalPages)
                .build();
    }

    @Override
    public ApiError createValidationErrorResponse(String message, List<FieldError> errors) {
        return ApiError.builder()
                .success(false)
                .code(404)
                .message(message)
                .errors(errors)
                .build();
    }

    @Override
    public ApiError createErrorResponse(int code, String message) {
        return ApiError.builder()
                .success(false)
                .code(code)
                .message(message)
                .build();
    }
}
