package it.unicam.cs.ids.context.catalog.application.mappers;

import it.unicam.cs.ids.context.catalog.domain.model.Purchase;
import it.unicam.cs.ids.context.catalog.infrastructure.web.dtos.PurchaseDTO;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring", 
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        uses = {ProductMapper.class, BundleMapper.class})
@Component

public abstract class PurchaseMapper {
    public abstract PurchaseDTO toDto(Purchase purchase);
}