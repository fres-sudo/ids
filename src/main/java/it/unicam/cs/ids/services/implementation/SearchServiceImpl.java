package it.unicam.cs.ids.services.implementation;

import it.unicam.cs.ids.mappers.BundleMapper;
import it.unicam.cs.ids.services.SearchService;
import it.unicam.cs.ids.shared.infrastructure.specifications.BundleSpecification;
import it.unicam.cs.ids.dto.BundleDTO;
import it.unicam.cs.ids.dto.CompanyDTO;
import it.unicam.cs.ids.dto.ProductDTO;
import it.unicam.cs.ids.shared.infrastructure.api.filters.BundleFilter;
import it.unicam.cs.ids.shared.infrastructure.api.filters.CompanyFilter;
import it.unicam.cs.ids.shared.infrastructure.api.filters.ProductFilter;
import it.unicam.cs.ids.model.Bundle;
import it.unicam.cs.ids.model.Company;
import it.unicam.cs.ids.model.Product;
import it.unicam.cs.ids.mappers.CompanyMapper;
import it.unicam.cs.ids.mappers.ProductMapper;
import it.unicam.cs.ids.repositories.BundleRepository;
import it.unicam.cs.ids.repositories.CompanyRepository;
import it.unicam.cs.ids.repositories.ProductRepository;
import it.unicam.cs.ids.shared.infrastructure.specifications.CompanySpecification;
import it.unicam.cs.ids.shared.infrastructure.specifications.ProductSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class SearchServiceImpl implements SearchService {

    private final ProductRepository productRepository;
    private final BundleRepository bundleRepository;
    private final CompanyRepository companyRepository;
    private final ProductMapper productMapper;
    private final CompanyMapper companyMapper;
    private final BundleMapper bundleMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<ProductDTO> searchProducts(ProductFilter filter, Pageable pageable) {
        Specification<Product> spec = ProductSpecification.withFilter(filter);

        Page<Product> products = productRepository.findAll(spec, pageable);
        return products.map(productMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BundleDTO> searchBundles(BundleFilter filter, Pageable pageable) {
        Specification<Bundle> spec = BundleSpecification.withFilter(filter);

        Page<Bundle> bundles = bundleRepository.findAll(spec, pageable);
        return bundles.map(bundleMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CompanyDTO> searchCompanies(CompanyFilter filter, Pageable pageable) {
        Specification<Company> spec = CompanySpecification.withFilter(filter);

        Page<Company> companies = companyRepository.findAll(spec, pageable);
        return companies.map(companyMapper::toDto);
    }
}