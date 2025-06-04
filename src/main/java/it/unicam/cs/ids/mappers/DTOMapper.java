package it.unicam.cs.ids.mappers;
import it.unicam.cs.ids.dtos.DTO;
/**
 * This interface defines methods for converting between Data Transfer Objects (DTOs) and Entities.
 * It is a generic interface that can be implemented for different types of DTOs and Entities.
 *
 * @param <DTO_TP> the type of the Data Transfer Object
 * @param <Entity> the type of the Entity
 */
public interface DTOMapper<DTO_TP extends DTO, Entity> {
    /**
     * Converts a DTO to an Entity.
     *
     * @param dto the DTO to convert
     * @return the converted Entity
     */
    Entity fromDTO(DTO_TP dto);

    /**
     * Converts an Entity to a DTO.
     *
     * @param entity the Entity to convert
     * @return the converted DTO
     */
    DTO_TP toDTO(Entity entity);
}
