package it.unicam.cs.ids.context.catalog.application.mappers;

import it.unicam.cs.ids.context.catalog.infrastructure.web.dtos.BundleDTO;
import it.unicam.cs.ids.context.catalog.infrastructure.web.dtos.requests.CreateBundleRequest;
import it.unicam.cs.ids.context.catalog.domain.model.Bundle;
import it.unicam.cs.ids.context.catalog.infrastructure.web.dtos.requests.UpdateBundleRequest;
import it.unicam.cs.ids.context.company.application.mappers.CompanyMapper;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring",
        uses = {BundledProductMapper.class, CompanyMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
@Component
public abstract class BundleMapper {
    @Autowired
    protected BundledProductMapper bundledProductMapper;

    public abstract BundleDTO toDto(Bundle bundle);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "status", expression = "java(it.unicam.cs.ids.context.catalog.domain.model.ApprovalStatus.DRAFT)")
    @Mapping(target = "distributor", source = "distributorId", qualifiedByName = "mapCompanyById")
    @Mapping(target = "products", ignore = true)
    @Mapping(target = "estimatedDeliveryDays", source = "estimatedDeliveryTime")
    public abstract Bundle fromRequest(CreateBundleRequest dto);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "category", source = "category")
    @Mapping(target = "tags", source = "tags")
    @Mapping(target = "quantity", source = "quantity")
    @Mapping(target = "discountPercentage", source = "discountPercentage")
    @Mapping(target = "currency", source = "currency")
    @Mapping(target = "availableForSale", source = "availableForSale")
    @Mapping(target = "availableForShipping", source = "availableForShipping")
    @Mapping(target = "estimatedDeliveryDays", source = "estimatedDeliveryTime")
    @Mapping(target = "shippingCost", source = "shippingCost")
    @Mapping(target = "returnPolicy", source = "returnPolicy")
    @Mapping(target = "bundleLocation", source = "bundleLocation")
    @Mapping(target = "distributor", ignore = true)
    @Mapping(target = "products", source = "bundledProducts")
    public abstract Bundle updateFromRequest(@MappingTarget Bundle existing, UpdateBundleRequest request);
}

