package it.unicam.cs.ids.services;

import it.unicam.cs.ids.dto.BundleDTO;
import it.unicam.cs.ids.dto.CompanyDTO;
import it.unicam.cs.ids.dto.ProductDTO;
import it.unicam.cs.ids.shared.infrastructure.api.filters.BundleFilter;
import it.unicam.cs.ids.shared.infrastructure.api.filters.CompanyFilter;
import it.unicam.cs.ids.shared.infrastructure.api.filters.ProductFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SearchService {
    Page<ProductDTO> searchProducts(ProductFilter filter, Pageable pageable);
    Page<BundleDTO> searchBundles(BundleFilter filter, Pageable pageable);
    Page<CompanyDTO> searchCompanies(CompanyFilter filter, Pageable pageable);
}