package it.unicam.cs.ids.context.identity.application.mappers;

import it.unicam.cs.ids.context.identity.domain.model.RoleRequest;
import it.unicam.cs.ids.context.identity.infrastructure.web.dtos.RoleRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for converting between RoleRequest entity and RoleRequestDTO.
 * Uses MapStruct for automatic mapping generation.
 */
@Mapper(componentModel = "spring")
public interface RoleRequestMapper {
    
    @Mapping(source = "requestingUser.id", target = "requestingUserId")
    @Mapping(source = "requestingUser.email", target = "requestingUserEmail") 
    @Mapping(source = "requestingUser.name", target = "requestingUserName")
    RoleRequestDTO toDto(RoleRequest roleRequest);
}