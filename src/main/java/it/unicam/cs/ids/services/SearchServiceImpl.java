package it.unicam.cs.ids.services;

import it.unicam.cs.ids.api.responses.factories.ApiResponseFactory;
import it.unicam.cs.ids.api.responses.factories.DefaultApiResponseFactory;
import it.unicam.cs.ids.dtos.filters.BundleFilter;
import it.unicam.cs.ids.dtos.filters.CompanyFilter;
import it.unicam.cs.ids.dtos.filters.ProductFilter;
import it.unicam.cs.ids.entities.Bundle;
import it.unicam.cs.ids.entities.Company;
import it.unicam.cs.ids.entities.Product;
import it.unicam.cs.ids.enums.SortDirection;
import it.unicam.cs.ids.repositories.BundleRepository;
import it.unicam.cs.ids.repositories.CompanyRepository;
import it.unicam.cs.ids.repositories.ProductRepository;
import it.unicam.cs.ids.services.specifications.CompanySpecification;
import it.unicam.cs.ids.services.specifications.ProductSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class SearchServiceImpl implements SearchService {

    private final ApiResponseFactory apiResponseFactory = new DefaultApiResponseFactory();
    private final ProductRepository productRepository;
    private final BundleRepository bundleRepository;
    private final CompanyRepository companyRepository;

    @Autowired
    public SearchServiceImpl(ProductRepository productRepository, BundleRepository bundleRepository, CompanyRepository companyRepository) {
        this.productRepository = productRepository;
        this.bundleRepository = bundleRepository;
        this.companyRepository = companyRepository;
    }

    @Override
    public Page<Product> searchProducts(ProductFilter filter) {
        Specification<Product> spec = ProductSpecification.withFilter(filter);

        Sort sort = Sort.unsorted(); // Default to unsorted

        if (filter.getSortBy() != null && !filter.getSortBy().isEmpty()) {
            Sort.Direction direction = filter.getSortDirection() == SortDirection.DESC ? Sort.Direction.DESC : Sort.Direction.ASC;
            sort = Sort.by(direction, filter.getSortBy());
        }

        Pageable pageable = PageRequest.of(filter.getPageNo(), filter.getPageSize(), sort);

        return productRepository.findAll(spec, pageable);
    }

    public Page<Bundle> searchBundles(BundleFilter filter) {
        Sort sort = Sort.by(
            filter.getSortDirection() == SortDirection.DESC ? Sort.Direction.DESC : Sort.Direction.ASC,
            filter.getSortBy() != null ? filter.getSortBy() : "id"
        );
        return bundleRepository.findAll(PageRequest.of(filter.getPageNo(), filter.getPageSize(), sort));

    }

    @Override
    public Page<Company> searchCompanies(CompanyFilter filter) {
        Specification<Company> spec = CompanySpecification.withFilter(filter);

        Sort sort = Sort.unsorted();

        if (filter.getSortBy() != null && !filter.getSortBy().isEmpty()) {
            Sort.Direction direction = filter.getSortDirection() == SortDirection.DESC ? Sort.Direction.DESC : Sort.Direction.ASC;
            sort = Sort.by(direction, filter.getSortBy());
        }
        
        Pageable pageable = PageRequest.of(filter.getPageNo(), filter.getPageSize(), sort);

        return companyRepository.findAll(spec, pageable);
    }
}