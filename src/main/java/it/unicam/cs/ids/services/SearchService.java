package it.unicam.cs.ids.services;

import it.unicam.cs.ids.dtos.CompanyDTO;
import it.unicam.cs.ids.dtos.ProductDTO;
import it.unicam.cs.ids.dtos.filters.BundleFilter;
import it.unicam.cs.ids.dtos.filters.CompanyFilter;
import it.unicam.cs.ids.dtos.filters.ProductFilter;
import it.unicam.cs.ids.entities.Bundle;
import it.unicam.cs.ids.entities.Company;
import org.springframework.data.domain.Page;

public interface SearchService {
    Page<ProductDTO> searchProducts(ProductFilter filter);
    Page<Bundle> searchBundles(BundleFilter filter);
    Page<CompanyDTO> searchCompanies(CompanyFilter filter);
}