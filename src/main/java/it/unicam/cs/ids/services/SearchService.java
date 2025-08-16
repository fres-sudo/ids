package it.unicam.cs.ids.services;

import it.unicam.cs.ids.dto.BundleDTO;
import it.unicam.cs.ids.dto.CompanyDTO;
import it.unicam.cs.ids.dto.ProductDTO;
import it.unicam.cs.ids.shared.infrastructure.api.filters.BundleFilter;
import it.unicam.cs.ids.shared.infrastructure.api.filters.CompanyFilter;
import it.unicam.cs.ids.shared.infrastructure.api.filters.ProductFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * SearchService defines the operations related to searching products, bundles, and companies.
 */
public interface SearchService {
    /**
     * Searches for products based on the provided filter and pagination information.
     *
     * @param filter the filter criteria for searching products
     * @param pageable pagination information
     * @return a page of ProductDTO objects matching the search criteria
     */
    Page<ProductDTO> searchProducts(ProductFilter filter, Pageable pageable);

    /**
     * Searches for bundles based on the provided filter and pagination information.
     *
     * @param filter the filter criteria for searching bundles
     * @param pageable pagination information
     * @return a page of BundleDTO objects matching the search criteria
     */
    Page<BundleDTO> searchBundles(BundleFilter filter, Pageable pageable);

    /**
     * Searches for companies based on the provided filter and pagination information.
     *
     * @param filter the filter criteria for searching companies
     * @param pageable pagination information
     * @return a page of CompanyDTO objects matching the search criteria
     */
    Page<CompanyDTO> searchCompanies(CompanyFilter filter, Pageable pageable);

}