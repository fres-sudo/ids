package it.unicam.cs.ids.context.catalog.application.mappers;

import it.unicam.cs.ids.context.catalog.infrastructure.web.dtos.ProductDTO;
import it.unicam.cs.ids.context.catalog.infrastructure.web.dtos.requests.CreateProductRequest;
import it.unicam.cs.ids.context.catalog.domain.model.Product;
import it.unicam.cs.ids.context.certification.application.mappers.CertificateMapper;
import it.unicam.cs.ids.context.company.application.mappers.CompanyMapper;
import it.unicam.cs.ids.context.company.domain.repositories.CompanyRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring", uses = {CompanyMapper.class, CertificateMapper.class})
@Component
public abstract class ProductMapper {

    @Autowired
    protected CompanyRepository companyRepository;

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "status", expression = "java(it.unicam.cs.ids.context.catalog.domain.model.ApprovalStatus.DRAFT)")
    @Mapping(target = "producer", source = "producerId", qualifiedByName = "mapCompanyById")
    @Mapping(target = "creator", source = "creatorId", qualifiedByName = "mapCompanyById")
    @Mapping(target = "distributors", source = "distributorId", qualifiedByName = "mapCompanyByIdMany")
    @Mapping(target = "transformers", source = "transformerId", qualifiedByName = "mapCompanyByIdMany")
    @Mapping(target = "certificates", ignore = true)
    @Mapping(target = "imageUrls", ignore = true)
    public abstract Product fromRequest(CreateProductRequest dto);

    @Mapping(target = "company", source = "producer")
    @Mapping(target = "unitOfMeasure", source = "unityOfMeasure")
    public abstract ProductDTO toDto(Product product);
}