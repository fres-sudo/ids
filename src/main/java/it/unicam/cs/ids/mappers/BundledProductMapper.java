package it.unicam.cs.ids.mappers;

import it.unicam.cs.ids.web.requests.company.CreateBundledProductRequest;
import it.unicam.cs.ids.models.BundledProduct;
import it.unicam.cs.ids.models.Product;
import it.unicam.cs.ids.repositories.ProductRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Component;

/**
 * Mapper for converting CreateBundledProductRequest to BundledProduct entity.
 * This mapper uses MapStruct to handle the conversion and includes a custom method
 */
@Mapper(componentModel = "spring")
@Component
public abstract class BundledProductMapper {
    /** Repository for accessing product data. */
    protected ProductRepository productRepository; // Inject the repository

    /**
     * Maps a CreateBundledProductRequest to a BundledProduct entity.
     * This method ignores the ID field since it is auto-generated.
     *
     * @param dto the DTO containing the details of the bundled product to be created
     * @return a new BundledProduct instance with the details from the DTO
     */
    @Mapping(target = "id", ignore = true) // ID is auto-generated
    @Mapping(target = "product", source = "productId", qualifiedByName = "mapProductIdToProduct")
    public abstract BundledProduct toEntity(CreateBundledProductRequest dto);


    /**
     * Maps a product ID to the corresponding Product entity.
     * This method retrieves the product from the repository based on the provided ID.
     *
     * @param productId the ID of the product to be retrieved
     * @return the Product entity associated with the given ID
     * @throws EntityNotFoundException if no product is found with the given ID
     */
    @Named("mapProductIdToProduct")
    protected Product mapProductIdToProduct(long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product with ID " + productId + " not found."));
    }
}