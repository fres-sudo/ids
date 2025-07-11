package it.unicam.cs.ids.api.controllers;

import it.unicam.cs.ids.dtos.CompanyDTO;
import it.unicam.cs.ids.dtos.ProductDTO;
import it.unicam.cs.ids.dtos.filters.BundleFilter;
import it.unicam.cs.ids.dtos.filters.CompanyFilter;
import it.unicam.cs.ids.dtos.filters.ProductFilter;
import it.unicam.cs.ids.entities.Bundle;
import it.unicam.cs.ids.entities.Company;
import it.unicam.cs.ids.entities.Product;
import it.unicam.cs.ids.services.SearchService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/search") // Base path for search operations
public class SearchController {

    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @PostMapping(path = "/products")
    ResponseEntity<Page<ProductDTO>> searchProducts(@RequestBody ProductFilter filterParam) {
        Page<ProductDTO> response = searchService.searchProducts(filterParam);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(path = "/bundles")
    ResponseEntity<Page<Bundle>> searchBundles(@RequestBody BundleFilter filterParam) {
        Page<Bundle> response = searchService.searchBundles(filterParam);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(path = "/companies", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Page<CompanyDTO>> searchCompanies(@RequestBody CompanyFilter filterParam) {
        Page<CompanyDTO> response = searchService.searchCompanies(filterParam);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}