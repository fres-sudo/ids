package it.unicam.cs.ids.mappers;

import it.unicam.cs.ids.models.Purchase;
import it.unicam.cs.ids.dto.PurchaseDTO;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

/**
 * Mapper interface for converting between Purchase and PurchaseDTO.
 */
@Mapper(componentModel = "spring", 
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        uses = {ProductMapper.class, BundleMapper.class})
@Component
public abstract class PurchaseMapper {
    /**
     * Maps a Purchase entity to a PurchaseDTO.
     *
     * @param purchase the Purchase entity to convert
     * @return a PurchaseDTO containing the details of the purchase
     */
    public abstract PurchaseDTO toDto(Purchase purchase);
}