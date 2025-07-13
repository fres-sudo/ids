package it.unicam.cs.ids.context.catalog.application.mappers;

import it.unicam.cs.ids.context.catalog.infrastructure.web.dtos.BundleDTO;
import it.unicam.cs.ids.context.catalog.infrastructure.web.dtos.requests.CreateBundleRequest;
import it.unicam.cs.ids.context.catalog.infrastructure.web.dtos.requests.CreateBundledProductRequest;
import it.unicam.cs.ids.context.catalog.domain.model.Bundle;
import it.unicam.cs.ids.context.catalog.domain.model.BundledProduct;
import it.unicam.cs.ids.context.company.application.mappers.CompanyMapper;
import it.unicam.cs.ids.context.company.domain.models.Company;
import it.unicam.cs.ids.context.company.domain.repositories.CompanyRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;

@Mapper(componentModel = "spring",uses = {BundledProductMapper.class, CompanyMapper.class})
@Component
public abstract class BundleMapper {

    @Autowired
    protected CompanyRepository companyRepository;

    @Autowired
    protected BundledProductMapper bundledProductMapper;

    public abstract BundleDTO toDto(Bundle bundle);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "status", expression = "java(it.unicam.cs.ids.context.catalog.domain.model.ApprovalStatus.PENDING)")
    @Mapping(target = "distributor", source = "distributorId", qualifiedByName = "mapCompanyIdToCompany")
    @Mapping(target = "products", source = "bundledProducts")
    @Mapping(target = "creator", source = "distributorId", qualifiedByName = "getCompanyById")
    @Mapping(target = "estimatedDeliveryDays", source = "estimatedDeliveryTime")
    public abstract Bundle fromRequest(CreateBundleRequest dto);

    @Named("mapCompanyIdToCompany")
    protected Company mapCompanyIdToCompany(Long companyId) {
        return companyRepository.findById(companyId)
                .orElseThrow(() -> new EntityNotFoundException("Company with ID " + companyId + " not found."));
    }

    @Named("getCompanyById")
    protected Company getCompanyById(Long companyId) {
        return companyRepository.findById(companyId)
                .orElseThrow(() -> new EntityNotFoundException("Company with ID " + companyId + " not found."));
    }
    // Special method to link BundledProducts back to the Bundle
    protected List<BundledProduct> mapBundledProducts(List<CreateBundledProductRequest> bundledProductDTOs, @MappingTarget Bundle bundle) {
        if (bundledProductDTOs == null) {
            return null;
        }
        return bundledProductDTOs.stream()
                .map(bundledProductDTO -> {
                    BundledProduct bp = bundledProductMapper.toEntity(bundledProductDTO);
                    bp.setBundle(bundle);
                    return bp;
                })
                .toList();
    }
}

