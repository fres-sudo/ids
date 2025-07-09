package it.unicam.cs.ids.mappers;

import it.unicam.cs.ids.dtos.ProductDTO;
import it.unicam.cs.ids.dtos.requests.CreateProductRequest;
import it.unicam.cs.ids.entities.Product;
import it.unicam.cs.ids.repositories.CompanyRepository;
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
    @Mapping(target = "status", constant = "DRAFT")
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