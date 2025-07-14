package it.unicam.cs.ids.context.catalog.application.services;

import it.unicam.cs.ids.context.catalog.application.mappers.BundleMapper;
import it.unicam.cs.ids.context.catalog.infrastructure.persistence.specifications.BundleSpecification;
import it.unicam.cs.ids.context.catalog.infrastructure.web.dtos.BundleDTO;
import it.unicam.cs.ids.context.company.infrastructure.web.dtos.CompanyDTO;
import it.unicam.cs.ids.context.catalog.infrastructure.web.dtos.ProductDTO;
import it.unicam.cs.ids.context.catalog.infrastructure.web.dtos.BundleFilter;
import it.unicam.cs.ids.context.company.infrastructure.web.dtos.CompanyFilter;
import it.unicam.cs.ids.context.catalog.infrastructure.web.dtos.ProductFilter;
import it.unicam.cs.ids.context.catalog.domain.model.Bundle;
import it.unicam.cs.ids.context.company.domain.models.Company;
import it.unicam.cs.ids.context.catalog.domain.model.Product;
import it.unicam.cs.ids.shared.kernel.enums.SortDirection;
import it.unicam.cs.ids.context.company.application.mappers.CompanyMapper;
import it.unicam.cs.ids.context.catalog.application.mappers.ProductMapper;
import it.unicam.cs.ids.context.catalog.domain.repositories.BundleRepository;
import it.unicam.cs.ids.context.company.domain.repositories.CompanyRepository;
import it.unicam.cs.ids.context.catalog.domain.repositories.ProductRepository;
import it.unicam.cs.ids.context.company.infrastructure.persistence.specifications.CompanySpecification;
import it.unicam.cs.ids.context.catalog.infrastructure.persistence.specifications.ProductSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public Page<ProductDTO> searchProducts(ProductFilter filter) {
        Specification<Product> spec = ProductSpecification.withFilter(filter);
        Sort sort = Sort.by(
                filter.getSortDirection().getValue(),
                filter.getSortBy() != null ? filter.getSortBy() : "id"
        );

        Pageable pageable = PageRequest.of(filter.getPageNo(), filter.getPageSize(), sort);

        Page<Product> products = productRepository.findAll(spec, pageable);
        return products.map(productMapper::toDto);
    }

    public Page<BundleDTO> searchBundles(BundleFilter filter) {
        Specification<Bundle> spec = BundleSpecification.withFilter(filter);
        Sort sort = Sort.by(
            filter.getSortDirection().getValue(),
            filter.getSortBy() != null ? filter.getSortBy() : "id"
        );

        Pageable pageable = PageRequest.of(filter.getPageNo(), filter.getPageSize(), sort);

        Page<Bundle> bundles = bundleRepository.findAll(spec, pageable);
        return bundles.map(bundleMapper::toDto);
    }

    @Override
    public Page<CompanyDTO> searchCompanies(CompanyFilter filter) {
        Specification<Company> spec = CompanySpecification.withFilter(filter);

        Sort sort = Sort.by(
            filter.getSortDirection().getValue(),
            filter.getSortBy() != null ? filter.getSortBy() : "id"
        );

        Pageable pageable = PageRequest.of(filter.getPageNo(), filter.getPageSize(), sort);

        Page<Company> companies = companyRepository.findAll(spec, pageable);
        return companies.map(companyMapper::toDto);
    }
}