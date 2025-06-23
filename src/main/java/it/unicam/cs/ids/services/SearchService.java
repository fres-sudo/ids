package it.unicam.cs.ids.services;

import it.unicam.cs.ids.dtos.filters.BundleFilter;
import it.unicam.cs.ids.dtos.filters.CompanyFilter;
import it.unicam.cs.ids.dtos.filters.ProductFilter;
import it.unicam.cs.ids.entities.Bundle;
import it.unicam.cs.ids.entities.Company;
import it.unicam.cs.ids.entities.Product;
import org.springframework.data.domain.Page;

public interface SearchService {
    Page<Product> searchProducts(ProductFilter filter);
    Page<Bundle> searchBundles(BundleFilter filter);
    Page<Company> searchCompanies(CompanyFilter filter);
}