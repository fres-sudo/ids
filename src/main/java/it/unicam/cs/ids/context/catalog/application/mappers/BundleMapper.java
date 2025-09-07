package it.unicam.cs.ids.context.catalog.application.mappers;

import it.unicam.cs.ids.context.catalog.infrastructure.web.dtos.BundleDTO;
import it.unicam.cs.ids.context.catalog.infrastructure.web.dtos.requests.CreateBundleRequest;
import it.unicam.cs.ids.context.catalog.domain.model.Bundle;
import it.unicam.cs.ids.context.catalog.infrastructure.web.dtos.requests.UpdateBundleRequest;
import it.unicam.cs.ids.context.company.application.mappers.CompanyMapper;
import lombok.RequiredArgsConstructor;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring",
        uses = {BundledProductMapper.class, CompanyMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public abstract class BundleMapper {
    public abstract BundleDTO toDto(Bundle bundle);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "approvalStatus", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "distributor", source = "distributorId", qualifiedByName = "mapCompanyById")
    @Mapping(target = "products", ignore = true)
    @Mapping(target = "estimatedDeliveryDays", source = "estimatedDeliveryTime")
    public abstract Bundle fromCreateRequest(CreateBundleRequest dto);

    @Mapping(target = "estimatedDeliveryDays", source = "estimatedDeliveryTime")
    @Mapping(target = "distributor", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "approvalStatus", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "products", source = "bundledProducts")
    public abstract Bundle updateFromRequest(@MappingTarget Bundle existing, UpdateBundleRequest request);
}

