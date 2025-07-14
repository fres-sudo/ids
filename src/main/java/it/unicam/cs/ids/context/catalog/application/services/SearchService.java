package it.unicam.cs.ids.context.catalog.application.services;

import it.unicam.cs.ids.context.catalog.infrastructure.web.dtos.BundleDTO;
import it.unicam.cs.ids.context.company.infrastructure.web.dtos.CompanyDTO;
import it.unicam.cs.ids.context.catalog.infrastructure.web.dtos.ProductDTO;
import it.unicam.cs.ids.context.catalog.infrastructure.web.dtos.BundleFilter;
import it.unicam.cs.ids.context.company.infrastructure.web.dtos.CompanyFilter;
import it.unicam.cs.ids.context.catalog.infrastructure.web.dtos.ProductFilter;
import it.unicam.cs.ids.context.catalog.domain.model.Bundle;
import org.springframework.data.domain.Page;

public interface SearchService {
    Page<ProductDTO> searchProducts(ProductFilter filter);
    Page<BundleDTO> searchBundles(BundleFilter filter);
    Page<CompanyDTO> searchCompanies(CompanyFilter filter);
}