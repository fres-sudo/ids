package it.unicam.cs.ids.mappers;

import it.unicam.cs.ids.model.Product;
import it.unicam.cs.ids.dto.ProductDTO;
import it.unicam.cs.ids.web.requests.company.CreateProductRequest;
import it.unicam.cs.ids.web.requests.company.UpdateProductRequest;
import it.unicam.cs.ids.repositories.CompanyRepository;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, unmappedSourcePolicy = ReportingPolicy.IGNORE, uses = {CompanyMapper.class, CertificateMapper.class})
@Component
public abstract class ProductMapper {

    @Autowired
    protected CompanyRepository companyRepository;

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

    @Mapping(target = "company", source = "producer")
    public abstract ProductDTO toDto(Product product);

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