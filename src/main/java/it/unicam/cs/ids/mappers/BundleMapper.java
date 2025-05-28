package it.unicam.cs.ids.mappers;

import it.unicam.cs.ids.dtos.BundleDTO;
import it.unicam.cs.ids.entities.Bundle;

public class BundleMapper implements DTOMapper<BundleDTO, Bundle> {

    private static final BundledProductMapper bundledProductMapper = new BundledProductMapper();

    @Override
    public BundleDTO toDTO(Bundle entity) {
        if (entity == null) {
            return null;
        }
        BundleDTO dto = new BundleDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setPrice(entity.getPrice());
        dto.setProducts(entity.getProducts().stream()
                .map(bundledProductMapper::toDTO)
                .toList());
        return dto;
    }

    @Override
    public Bundle fromDTO(BundleDTO dto) {
        if (dto == null) {
            return null;
        }
        Bundle entity = new Bundle();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());
        entity.setProducts(dto.getProducts().stream()
                .map(bundledProductMapper::fromDTO)
                .toList());
        return entity;
    }
}
