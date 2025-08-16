package it.unicam.cs.ids.mappers;

import it.unicam.cs.ids.models.Product;
import it.unicam.cs.ids.dto.ProductDTO;
import it.unicam.cs.ids.web.requests.company.CreateProductRequest;
import it.unicam.cs.ids.web.requests.company.UpdateProductRequest;
import it.unicam.cs.ids.repositories.CompanyRepository;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Mapper for converting between Product entities and DTOs.
 */
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, unmappedSourcePolicy = ReportingPolicy.IGNORE, uses = {CompanyMapper.class, CertificateMapper.class})
@Component
public abstract class ProductMapper {
    /** Repository for accessing company data. */
    @Autowired
    protected CompanyRepository companyRepository;

    /**
     * Maps a {@link CreateProductRequest} to a {@link Product} entity.
     * @param dto the DTO containing the details of the product to be created
     * @return a new Product instance populated with values from the request
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "status", expression = "java(it.unicam.cs.ids.shared.kernel.enums.ApprovalStatus.DRAFT)")
    @Mapping(target = "producer", source = "producerId", qualifiedByName = "mapCompanyById")
    @Mapping(target = "creator", source = "creatorId", qualifiedByName = "mapCompanyById")
    @Mapping(target = "distributors", source = "distributorId", qualifiedByName = "mapCompanyByIdMany")
    @Mapping(target = "transformers", source = "transformerId", qualifiedByName = "mapCompanyByIdMany")
    @Mapping(target = "certificates", ignore = true)
    @Mapping(target = "imageUrls", ignore = true)
    public abstract Product fromRequest(CreateProductRequest dto);

    /**
     * Converts a {@link Product} entity to a {@link ProductDTO}.
     * This method maps the fields of the Product entity to the corresponding fields in the DTO.
     *
     * @param product the Product entity to be converted
     * @return a new ProductDTO instance with the details from the Product entity
     */
    @Mapping(target = "company", source = "producer")
    public abstract ProductDTO toDto(Product product);

   /**
    * Updates an existing {@link Product} entity with the data from an {@link UpdateProductRequest}.
    * The method maps the request fields to the corresponding Product fields,
    * updating the existing entity in place.
    * @param request the request containing updated product details
    * @param product the existing Product entity to be updated
    * @return the updated Product entity
    */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", expression = "java(new java.util.Date())")
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "distributors", source = "distributorId", qualifiedByName = "mapCompanyByIdMany")
    @Mapping(target = "transformers", source = "transformerId", qualifiedByName = "mapCompanyByIdMany")
    @Mapping(target = "certificates", ignore = true)
    @Mapping(target = "imageUrls", ignore = true)
    @Mapping(target = "creator", ignore = true) // Creator should not be updated
    @Mapping(target = "producer", source = "producerId", qualifiedByName = "mapCompanyById")
    @Mapping(target = "status", expression = "java(it.unicam.cs.ids.shared.kernel.enums.ApprovalStatus.PENDING)")
    public abstract Product updateProductFromRequest(UpdateProductRequest request, @MappingTarget Product product);
}