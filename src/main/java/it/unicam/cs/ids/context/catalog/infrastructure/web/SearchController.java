package it.unicam.cs.ids.context.catalog.infrastructure.web;

import it.unicam.cs.ids.context.catalog.infrastructure.web.dtos.BundleDTO;
import it.unicam.cs.ids.context.company.infrastructure.web.dtos.CompanyDTO;
import it.unicam.cs.ids.context.catalog.infrastructure.web.dtos.ProductDTO;
import it.unicam.cs.ids.context.catalog.infrastructure.web.dtos.BundleFilter;
import it.unicam.cs.ids.context.company.infrastructure.web.dtos.CompanyFilter;
import it.unicam.cs.ids.context.catalog.infrastructure.web.dtos.ProductFilter;
import it.unicam.cs.ids.context.catalog.application.services.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/search") // Base path for search operations
public class SearchController {

    private final SearchService searchService;

    @PostMapping(path = "/products")
    ResponseEntity<Page<ProductDTO>> searchProducts(@RequestBody ProductFilter filterParam) {
        Page<ProductDTO> response = searchService.searchProducts(filterParam);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(path = "/bundles")
    ResponseEntity<Page<BundleDTO>> searchBundles(@RequestBody BundleFilter filterParam) {
        Page<BundleDTO> response = searchService.searchBundles(filterParam);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(path = "/companies", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Page<CompanyDTO>> searchCompanies(@RequestBody CompanyFilter filterParam) {
        Page<CompanyDTO> response = searchService.searchCompanies(filterParam);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}