package it.unicam.cs.ids.mappers;

import it.unicam.cs.ids.dtos.requests.CreateBundledProductRequest;
import it.unicam.cs.ids.entities.BundledProduct;
import it.unicam.cs.ids.entities.Product;
import it.unicam.cs.ids.repositories.ProductRepository;
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
    @Mapping(target = "quantityInBundle", source = "quantityInBundle")
    @Mapping(target = "pricePerUnit", source = "unitPriceInBundle")
    public abstract BundledProduct toEntity(CreateBundledProductRequest dto);


    // This is the custom mapping method for productId to Product entity
    @Named("mapProductIdToProduct")
    protected Product mapProductIdToProduct(long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product with ID " + productId + " not found."));
    }
}