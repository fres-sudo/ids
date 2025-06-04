package it.unicam.cs.ids.services;

import it.unicam.cs.ids.api.responses.models.PaginatedApiResponse;
import it.unicam.cs.ids.dtos.BundleDTO;
import it.unicam.cs.ids.dtos.ProductDTO;
import it.unicam.cs.ids.dtos.filters.BundleFilter;
import it.unicam.cs.ids.dtos.filters.FilterParam;
import it.unicam.cs.ids.dtos.filters.ProductFilter;

/**
 * Search Service defines the operations related to searching Items on the platform.
 * The search can be filtered many criteria, specified by {@link FilterParam}.
 */
public interface SearchService {
    /**
     * Searches for products and bundles based on the given query.
     *
     * @param type the type of entity to search for (e.g., "product" or "bundle")
     * @param filterParam the filter criteria for searching
     * @return a paginated response containing products or bundles matching the search criteria
     */
    PaginatedApiResponse<?> search(String type, FilterParam filterParam);
}
