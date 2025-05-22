package it.unicam.cs.ids.api.responses.factories;

import it.unicam.cs.ids.api.responses.models.ApiError;
import it.unicam.cs.ids.api.responses.models.ApiResponse;
import it.unicam.cs.ids.api.responses.models.FieldError;
import it.unicam.cs.ids.api.responses.models.PaginatedApiResponse;

import java.util.List;

public interface ApiResponseFactory {

    <T> ApiResponse<T> createSuccessResponse(String message, T data);

    <T> PaginatedApiResponse<T> createPaginatedResponse(
            String message,
            T data,
            int page,
            int size,
            int totalPages);

    ApiError createValidationErrorResponse(String message, List<FieldError> errors);

    ApiError createErrorResponse(int code, String message);
}
