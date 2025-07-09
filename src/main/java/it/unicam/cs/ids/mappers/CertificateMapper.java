package it.unicam.cs.ids.mappers;

import it.unicam.cs.ids.dtos.requests.CreateCertificateRequest;
import it.unicam.cs.ids.entities.Certificate;
import it.unicam.cs.ids.entities.Company;
import it.unicam.cs.ids.entities.Product;
import it.unicam.cs.ids.repositories.CertificateRepository;
import it.unicam.cs.ids.repositories.CompanyRepository;
import it.unicam.cs.ids.repositories.ProductRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.persistence.EntityNotFoundException;

@Mapper(componentModel = "spring")
@Component
public abstract class CertificateMapper{

    @Autowired
    protected ProductRepository productRepository;
    @Autowired
    protected CompanyRepository companyRepository;

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "issuer", source = "issuerId", qualifiedByName = "mapCompanyIdToCompany")
    @Mapping(target = "product", source = "productId", qualifiedByName = "mapProductId")
    @Mapping(target = "certificateUrl", ignore = true)
    public abstract Certificate fromCreateRequest(CreateCertificateRequest dto);

    @Named("mapCompanyIdToCompany")
    protected Company mapCompanyIdToCompany(Long companyId) {
        return companyRepository.findById(companyId)
                .orElseThrow(() -> new EntityNotFoundException("Company with ID " + companyId + " not found."));
    }

    @Named("mapProductId")
    protected Product mapProductId(Long productId) {
        return productRepository.findById(productId).orElseThrow(() -> new EntityNotFoundException("Company with ID " + productId + " not found."));
    }
}