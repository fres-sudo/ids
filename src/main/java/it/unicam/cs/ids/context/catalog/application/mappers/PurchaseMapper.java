package it.unicam.cs.ids.context.catalog.application.mappers;

import it.unicam.cs.ids.context.catalog.domain.model.Purchase;
import it.unicam.cs.ids.context.catalog.infrastructure.web.dtos.BundleDTO;
import it.unicam.cs.ids.context.catalog.infrastructure.web.dtos.ProductDTO;
import it.unicam.cs.ids.context.catalog.infrastructure.web.dtos.PurchaseDTO;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        uses = {ProductMapper.class, BundleMapper.class})
@Component
public abstract class PurchaseMapper {

    @Mapping(source = "product", target = "item")
    public abstract PurchaseDTO<ProductDTO> toProductDto(Purchase purchase);

    @Mapping(source = "bundle", target = "item")
    public abstract PurchaseDTO<BundleDTO> toBundleDto(Purchase purchase);

    public PurchaseDTO<?> toDto(Purchase purchase) {
        if (purchase.isProductPurchase()) {
            return toProductDto(purchase);
        } else if (purchase.isBundlePurchase()) {
            return toBundleDto(purchase);
        } else {
            throw new IllegalStateException("Purchase must contain either a product or a bundle");
        }
    }
}