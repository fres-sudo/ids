package it.unicam.cs.ids.mappers;

import it.unicam.cs.ids.dto.CertificateDTO;
import it.unicam.cs.ids.web.requests.company.CreateCertificateRequest;
import it.unicam.cs.ids.models.Certificate;
import it.unicam.cs.ids.models.Product;
import it.unicam.cs.ids.repositories.ProductRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import jakarta.persistence.EntityNotFoundException;

import java.util.List;

/*+
    * Mapper for converting CreateCertificateRequest to Certificate entity.
 */
@Mapper(componentModel = "spring", uses = {CompanyMapper.class})
@Component
public abstract class CertificateMapper{
    /** Repository for accessing product data. */
    protected ProductRepository productRepository;

    /**
     * Maps a CreateCertificateRequest to a Certificate entity.
     * This method ignores certain fields that are not applicable during the creation phase,
     * such as ID, createdAt, updatedAt, and deletedAt.
     *
     * @param dto the DTO containing the details of the certificate to be created
     * @return a new Certificate instance with the details from the DTO
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "issuer", source = "issuerId", qualifiedByName = "mapCompanyById")
    @Mapping(target = "product", source = "productId", qualifiedByName = "mapProductId")
    public abstract Certificate fromCreateRequest(CreateCertificateRequest dto);
    /**
     * Converts a {@link Certificate} entity to a {@link CertificateDTO}.
     * This method maps the fields of the Certificate entity to the corresponding fields in the DTO.
     *
     * @param certificate the Certificate entity to be converted
     * @return a new CertificateDTO instance with the details from the Certificate entity
     */
    public abstract CertificateDTO toDto(Certificate certificate);

    /**
     * Converts a list of {@link Certificate} entities to a list of {@link CertificateDTO}.
     * This method maps each Certificate entity in the list to its corresponding DTO.
     *
     * @param certificates the list of Certificate entities to be converted
     * @return a list of CertificateDTO instances with the details from the Certificate entities
     */
    public List<CertificateDTO> toDtoMany(List<Certificate> certificates) {
        if (certificates == null || certificates.isEmpty()) {
            return null;
        }
        return certificates.stream()
                .map(this::toDto)
                .toList();
    }

    /**
     * Maps a product ID to the corresponding Product entity.
     * This method retrieves the product from the repository based on the provided ID.
     *
     * @param productId the ID of the product to be retrieved
     * @return the Product entity associated with the given ID
     * @throws EntityNotFoundException if no product is found with the given ID
     */
    @Named("mapProductId")
    protected Product mapProductId(Long productId) {
        return productRepository.findById(productId).orElseThrow(() -> new EntityNotFoundException("Company with ID " + productId + " not found."));
    }
}