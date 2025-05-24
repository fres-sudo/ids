package it.unicam.cs.ids.api.responses.models;

import lombok.experimental.SuperBuilder;

/**
 * PaginatedApiResponse is a class that represents a paginated response from an API.
 * It extends the {@link ApiResponse} class and includes additional fields for pagination.
 * This class is used to encapsulate the response data along with pagination information.
 * @param <T> the type of the data in the response
 */
@SuperBuilder(toBuilder = true)
public class PaginatedApiResponse<T> extends ApiResponse<T> {
    /** The current page number. */
    private int page;
    /** The size of the page (number of items per page). */
    private int size;
    /**  Indicates if there is a next page. */
    private boolean hasNext;
    /** Indicates if there is a previous page. */
    private boolean hasPrevious;
    /** The total number of pages available. */
    private int totalPages;
}
