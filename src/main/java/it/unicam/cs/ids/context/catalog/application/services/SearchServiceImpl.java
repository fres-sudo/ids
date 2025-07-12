package it.unicam.cs.ids.context.catalog.application.services;

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

    @Override
    @Transactional(readOnly = true)
    public Page<ProductDTO> searchProducts(ProductFilter filter) {
        Specification<Product> spec = ProductSpecification.withFilter(filter);

        Sort sort = Sort.unsorted(); // Default to unsorted

        if (filter.getSortBy() != null && !filter.getSortBy().isEmpty()) {
            Sort.Direction direction = filter.getSortDirection() == SortDirection.DESC ? Sort.Direction.DESC : Sort.Direction.ASC;
            sort = Sort.by(direction, filter.getSortBy());
        }

        Pageable pageable = PageRequest.of(filter.getPageNo(), filter.getPageSize(), sort);

        Page<Product> products = productRepository.findAll(spec, pageable);
        return products.map(productMapper::toDto);
    }

    public Page<Bundle> searchBundles(BundleFilter filter) {
        Sort sort = Sort.by(
            filter.getSortDirection() == SortDirection.DESC ? Sort.Direction.DESC : Sort.Direction.ASC,
            filter.getSortBy() != null ? filter.getSortBy() : "id"
        );
        return bundleRepository.findAll(PageRequest.of(filter.getPageNo(), filter.getPageSize(), sort));

    }

    @Override
    public Page<CompanyDTO> searchCompanies(CompanyFilter filter) {
        Specification<Company> spec = CompanySpecification.withFilter(filter);

        Sort sort = Sort.unsorted();

        if (filter.getSortBy() != null && !filter.getSortBy().isEmpty()) {
            Sort.Direction direction = filter.getSortDirection() == SortDirection.DESC ? Sort.Direction.DESC : Sort.Direction.ASC;
            sort = Sort.by(direction, filter.getSortBy());
        }
        
        Pageable pageable = PageRequest.of(filter.getPageNo(), filter.getPageSize(), sort);

        System.out.println("Searching companies with specs: " + spec);

        Page<Company> companies = companyRepository.findAll(spec, pageable);
        return companies.map(companyMapper::toDto);
    }
}