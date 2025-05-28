package it.unicam.cs.ids.mappers;

import it.unicam.cs.ids.dtos.ProductDTO;
import it.unicam.cs.ids.entities.Product;

public class ProductMapper implements DTOMapper<ProductDTO, Product> {

    @Override
    public ProductDTO toDTO(Product entity) {
        if (entity == null) {
            return null;
        }
        ProductDTO dto = new ProductDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setCategory(entity.getCategory());
        return dto;
    }

    @Override
    public Product fromDTO(ProductDTO dto) {
        if (dto == null) {
            return null;
        }
        Product entity = new Product();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setCategory(dto.getCategory());
        return entity;
    }
}
