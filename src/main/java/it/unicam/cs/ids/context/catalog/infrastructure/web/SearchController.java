package it.unicam.cs.ids.context.catalog.infrastructure.web;

import it.unicam.cs.ids.context.catalog.infrastructure.web.dtos.BundleDTO;
import it.unicam.cs.ids.context.company.infrastructure.web.dtos.CompanyDTO;
import it.unicam.cs.ids.context.catalog.infrastructure.web.dtos.ProductDTO;
import it.unicam.cs.ids.context.catalog.infrastructure.web.dtos.BundleFilter;
import it.unicam.cs.ids.context.company.infrastructure.web.dtos.CompanyFilter;
import it.unicam.cs.ids.context.catalog.infrastructure.web.dtos.ProductFilter;
import it.unicam.cs.ids.context.catalog.application.services.SearchService;
import it.unicam.cs.ids.context.catalog.domain.model.ProductCategory;
import it.unicam.cs.ids.context.company.domain.models.CompanyRoles;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/search")
public class SearchController {

    private final SearchService searchService;

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