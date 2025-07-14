package it.unicam.cs.ids.context.certification.application.mappers;

import it.unicam.cs.ids.context.company.application.mappers.CompanyMapper;
import it.unicam.cs.ids.context.certification.infrastructure.web.dtos.CertificateDTO;
import it.unicam.cs.ids.context.certification.infrastructure.web.dtos.requests.CreateCertificateRequest;
import it.unicam.cs.ids.context.certification.domain.model.Certificate;
import it.unicam.cs.ids.context.catalog.domain.model.Product;
import it.unicam.cs.ids.context.catalog.domain.repositories.ProductRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.persistence.EntityNotFoundException;

import java.util.List;

@Mapper(componentModel = "spring", uses = {CompanyMapper.class})
@Component
public abstract class CertificateMapper{

    @Autowired
    protected ProductRepository productRepository;


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "issuer", source = "issuerId", qualifiedByName = "mapCompanyById")
    @Mapping(target = "product", source = "productId", qualifiedByName = "mapProductId")
    public abstract Certificate fromCreateRequest(CreateCertificateRequest dto);

    public abstract CertificateDTO toDto(Certificate certificate);

    public List<CertificateDTO> toDtoMany(List<Certificate> certificates) {
        if (certificates == null || certificates.isEmpty()) {
            return null;
        }
        return certificates.stream()
                .map(this::toDto)
                .toList();
    }

    @Named("mapProductId")
    protected Product mapProductId(Long productId) {
        return productRepository.findById(productId).orElseThrow(() -> new EntityNotFoundException("Company with ID " + productId + " not found."));
    }
}