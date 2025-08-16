package it.unicam.cs.ids.mappers;

import it.unicam.cs.ids.model.Purchase;
import it.unicam.cs.ids.dto.PurchaseDTO;
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