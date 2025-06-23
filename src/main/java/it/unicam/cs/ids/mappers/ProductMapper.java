package it.unicam.cs.ids.mappers;

import it.unicam.cs.ids.dtos.requests.CreateProductRequest;
import it.unicam.cs.ids.entities.Company;
import it.unicam.cs.ids.entities.Product;
import it.unicam.cs.ids.repositories.CompanyRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;

@Mapper(componentModel = "spring")
@Component
public abstract class ProductMapper {

    @Autowired
    protected CompanyRepository companyRepository;

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "status", constant = "DRAFT")
    @Mapping(target = "producer", source = "producerId", qualifiedByName = "mapCompanyIdToCompany")
    @Mapping(target = "creator", source = "creatorId", qualifiedByName = "mapCompanyIdToCompany")
    @Mapping(target = "distributors", source = "distributorId", qualifiedByName = "mapDistributorIds")
    @Mapping(target = "transformers", source = "transformerId", qualifiedByName = "mapTransformerIds")
    @Mapping(target = "certificates", ignore = true)
    @Mapping(target = "imageUrls", ignore = true)
    public abstract Product fromRequest(CreateProductRequest dto);

    @Named("mapCompanyIdToCompany")
    protected Company mapCompanyIdToCompany(Long companyId) {
        if (companyId == null) {
            return null;
        }
        return companyRepository.findById(companyId)
                .orElseThrow(() -> new EntityNotFoundException("Company with ID " + companyId + " not found."));
    }

    @Named("mapDistributorIds")
    protected List<Company> mapDistributorIds(List<Long> distributorIds) {
        if (distributorIds == null || distributorIds.isEmpty()) {
            return null;
        }
        return distributorIds.stream()
                .map(this::mapCompanyIdToCompany)
                .toList();
    }

    @Named("mapTransformerIds")
    protected List<Company> mapTransformerIds(List<Long> transformerIds) {
        if (transformerIds == null || transformerIds.isEmpty()) {
            return null;
        }
        return transformerIds.stream()
                .map(this::mapCompanyIdToCompany)
                .toList();
    }
}