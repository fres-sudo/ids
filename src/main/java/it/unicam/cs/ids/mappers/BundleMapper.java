package it.unicam.cs.ids.mappers;

import it.unicam.cs.ids.dto.BundleDTO;
import it.unicam.cs.ids.web.requests.company.CreateBundleRequest;
import it.unicam.cs.ids.models.Bundle;
import it.unicam.cs.ids.web.requests.company.UpdateBundleRequest;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Mapper for converting between {@link Bundle} and {@link BundleDTO} objects.
 */
@Mapper(componentModel = "spring",
        uses = {BundledProductMapper.class, CompanyMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
@Component
public abstract class BundleMapper {
    /** Mapper for converting bundled products within a bundle. */
    @Autowired
    protected BundledProductMapper bundledProductMapper;
    /** Mapper for converting company-related information. */
    public abstract BundleDTO toDto(Bundle bundle);

    /**
     * Converts a {@link CreateBundleRequest} to a {@link Bundle} entity.
     * The method maps the request fields to the corresponding Bundle fields,
     * setting the status to PENDING and ignoring certain fields.
     *
     * @param dto the request containing bundle creation details
     * @return a new Bundle instance populated with the request data
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "status", expression = "java(it.unicam.cs.ids.shared.kernel.enums.ApprovalStatus.PENDING)")
    @Mapping(target = "distributor", source = "distributorId", qualifiedByName = "mapCompanyById")
    @Mapping(target = "products", ignore = true)
    @Mapping(target = "estimatedDeliveryDays", source = "estimatedDeliveryTime")
    public abstract Bundle fromRequest(CreateBundleRequest dto);

    /**
     * Updates an existing {@link Bundle} entity with the data from an {@link UpdateBundleRequest}.
     * The method maps the request fields to the corresponding Bundle fields,
     * updating the existing entity in place.
     *
     * @param existing the existing Bundle entity to be updated
     * @param request  the request containing updated bundle details
     * @return the updated Bundle entity
     */
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

