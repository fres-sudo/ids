package it.unicam.cs.ids.controller;

import it.unicam.cs.ids.dto.BundleDTO;
import it.unicam.cs.ids.dto.CompanyDTO;
import it.unicam.cs.ids.dto.ProductDTO;
import it.unicam.cs.ids.shared.infrastructure.api.filters.BundleFilter;
import it.unicam.cs.ids.shared.infrastructure.api.filters.CompanyFilter;
import it.unicam.cs.ids.shared.infrastructure.api.filters.ProductFilter;
import it.unicam.cs.ids.services.SearchService;
import it.unicam.cs.ids.shared.kernel.enums.ProductCategory;
import it.unicam.cs.ids.shared.kernel.enums.CompanyRoles;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/** Controller for searching products, bundles, and companies in the catalog.
 * Provides endpoints to search based on various filters such as categories, availability, price range, etc.
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/search")
public class SearchController {
    /** Service for searching in the catalog */
    private final SearchService searchService;

    /** Searches for products based on various filters. */
    @GetMapping(path = "/products")
    Page<ProductDTO> searchProducts(
            @PageableDefault() Pageable pageable,
            @RequestParam(required = false) List<ProductCategory> categories,
            @RequestParam(required = false, defaultValue = "true") boolean available,
            @RequestParam(required = false, defaultValue = "true") boolean availableForShipping,
            @RequestParam(required = false) Long producerId,
            @RequestParam(required = false, defaultValue = "0") double minPrice,
            @RequestParam(required = false, defaultValue = "10000") double maxPrice,
            @RequestParam(required = false) String searchBy,
            @RequestParam(required = false) List<String> tags) {
        ProductFilter filter = ProductFilter.builder()
                .categories(categories)
                .available(available)
                .availableForShipping(availableForShipping)
                .producerId(producerId)
                .minPrice(minPrice)
                .maxPrice(maxPrice)
                .searchBy(searchBy)
                .tags(tags)
                .build();
        return searchService.searchProducts(filter, pageable);
    }

    /** Searches for bundles based on various filters. */
    @GetMapping(path = "/bundles")
    Page<BundleDTO> searchBundles(
            @PageableDefault() Pageable pageable,
            @RequestParam(required = false) List<ProductCategory> categories,
            @RequestParam(required = false, defaultValue = "true") boolean available,
            @RequestParam(required = false, defaultValue = "true") boolean availableForShipping,
            @RequestParam(required = false, defaultValue = "0") double minDiscountPercentage,            @RequestParam(required = false, defaultValue = "0") double minPrice,
            @RequestParam(required = false, defaultValue = "10000") double maxPrice,
            @RequestParam(required = false) String searchBy,
            @RequestParam(required = false) List<String> tags) {
        BundleFilter filter = BundleFilter.builder()
                .categories(categories)
                .available(available)
                .availableForShipping(availableForShipping)
                .minDiscountPercentage(minDiscountPercentage)
                .minPrice(minPrice)
                .maxPrice(maxPrice)
                .searchBy(searchBy)
                .tags(tags)
                .build();
        return searchService.searchBundles(filter, pageable);
    }

    /** Searches for companies based on their roles and other criteria. */
    @GetMapping(path = "/companies")
    Page<CompanyDTO> searchCompanies(
            @PageableDefault() Pageable pageable,
            @RequestParam(required = false) CompanyRoles companyRole,
            @RequestParam(required = false) String searchBy) {
        CompanyFilter filter = CompanyFilter.builder()
                .companyRole(companyRole)
                .searchBy(searchBy)
                .build();
        return searchService.searchCompanies(filter, pageable);
    }
}