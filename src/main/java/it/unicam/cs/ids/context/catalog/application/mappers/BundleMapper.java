package it.unicam.cs.ids.context.catalog.application.mappers;

import it.unicam.cs.ids.context.catalog.infrastructure.web.dtos.BundleDTO;
import it.unicam.cs.ids.context.catalog.infrastructure.web.dtos.requests.CreateBundleRequest;
import it.unicam.cs.ids.context.catalog.infrastructure.web.dtos.requests.CreateBundledProductRequest;
import it.unicam.cs.ids.context.catalog.domain.model.Bundle;
import it.unicam.cs.ids.context.catalog.domain.model.BundledProduct;
import it.unicam.cs.ids.context.company.application.mappers.CompanyMapper;
import it.unicam.cs.ids.context.company.domain.models.Company;
import it.unicam.cs.ids.context.company.domain.repositories.CompanyRepository;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;

@Mapper(componentModel = "spring",uses = {BundledProductMapper.class, CompanyMapper.class})
@Component
public abstract class BundleMapper {

    @Autowired
    protected BundledProductMapper bundledProductMapper;

    public abstract BundleDTO toDto(Bundle bundle);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "status", expression = "java(it.unicam.cs.ids.context.catalog.domain.model.ApprovalStatus.PENDING)")
    @Mapping(target = "distributor", source = "distributorId", qualifiedByName = "mapCompanyById")
    @Mapping(target = "products", ignore = true)
    @Mapping(target = "estimatedDeliveryDays", source = "estimatedDeliveryTime")
    public abstract Bundle fromRequest(CreateBundleRequest dto);


    @AfterMapping
    protected void linkBundledProducts(CreateBundleRequest dto, @MappingTarget Bundle bundle) {
        if (dto.getBundledProducts() != null) {
            List<BundledProduct> bundledProducts = dto.getBundledProducts().stream()
                    .map(bundledProductDTO -> {
                        BundledProduct bp = bundledProductMapper.toEntity(bundledProductDTO);
                        bp.setBundle(bundle); // Set the bundle reference
                        return bp;
                    })
                    .toList();
            bundle.setProducts(bundledProducts);
        }
    }
}

