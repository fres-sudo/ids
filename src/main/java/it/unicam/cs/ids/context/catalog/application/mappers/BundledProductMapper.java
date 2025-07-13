package it.unicam.cs.ids.context.catalog.application.mappers;

import it.unicam.cs.ids.context.catalog.infrastructure.web.dtos.requests.CreateBundledProductRequest;
import it.unicam.cs.ids.context.catalog.domain.model.BundledProduct;
import it.unicam.cs.ids.context.catalog.domain.model.Product;
import it.unicam.cs.ids.context.catalog.domain.repositories.ProductRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public abstract class BundledProductMapper {

    @Autowired
    protected ProductRepository productRepository; // Inject the repository

    // This method will be used to map CreateBundledProductRequest to BundledProduct
    @Mapping(target = "id", ignore = true) // ID is auto-generated
    @Mapping(target = "bundle", ignore = true) // Bundle will be set by the BundleMapper
    @Mapping(target = "product", source = "productId", qualifiedByName = "mapProductIdToProduct")
    public abstract BundledProduct toEntity(CreateBundledProductRequest dto);


    // This is the custom mapping method for productId to Product entity
    @Named("mapProductIdToProduct")
    protected Product mapProductIdToProduct(long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product with ID " + productId + " not found."));
    }
}