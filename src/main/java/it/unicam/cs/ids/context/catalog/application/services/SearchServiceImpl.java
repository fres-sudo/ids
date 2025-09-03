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
import it.unicam.cs.ids.context.company.application.mappers.CompanyMapper;
import it.unicam.cs.ids.context.catalog.application.mappers.ProductMapper;
import it.unicam.cs.ids.context.catalog.domain.repositories.BundleRepository;
import it.unicam.cs.ids.context.company.domain.repositories.CompanyRepository;
import it.unicam.cs.ids.context.catalog.domain.repositories.ProductRepository;
import it.unicam.cs.ids.context.company.infrastructure.persistence.specifications.CompanySpecification;
import it.unicam.cs.ids.context.catalog.infrastructure.persistence.specifications.ProductSpecification;
import it.unicam.cs.ids.context.events.application.mappers.EventMapper;
import it.unicam.cs.ids.context.events.domain.model.Event;
import it.unicam.cs.ids.context.events.domain.repositories.EventRepository;
import it.unicam.cs.ids.context.events.infrastructure.persistence.specifications.EventSpecification;
import it.unicam.cs.ids.context.events.infrastructure.web.dto.EventDTO;
import it.unicam.cs.ids.context.events.infrastructure.web.dto.EventFilter;
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
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;

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

    @Override
    @Transactional(readOnly = true)
    public Page<EventDTO> searchEvents(EventFilter filter, Pageable pageable) {
        Specification<Event> spec = EventSpecification.withFilter(filter);

        Page<Event> events = eventRepository.findAll(spec, pageable);
        return events.map(eventMapper::toDto);
    }
}