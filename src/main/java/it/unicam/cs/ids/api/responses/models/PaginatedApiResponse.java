package it.unicam.cs.ids.api.responses.models;

import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * PaginatedApiResponse is a class that represents a paginated response from an API.
 * It extends the {@link ApiResponse} class and includes additional fields for pagination.
 * This class is used to encapsulate the response data along with pagination information.
 * @param <T> the type of the data of the list in the response
 */
@SuperBuilder(toBuilder = true)
<<<<<<< HEAD
public class PaginatedApiResponse<T> extends ApiResponse<List<T>> {
=======
public class PaginatedApiResponse<T> extends ApiResponse<T> {
>>>>>>> 0f10234 ([ADD] spring: core dependencies)
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
