package it.unicam.cs.ids.context.identity.application.mappers;

import it.unicam.cs.ids.context.identity.infrastructure.web.dtos.CertifierRequestDTO;
import it.unicam.cs.ids.context.identity.domain.model.CertifierRequest;
import it.unicam.cs.ids.context.identity.domain.model.User;
import it.unicam.cs.ids.shared.application.Validator;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

/**
 * Mapper for converting between {@link User} and {@link CertifierRequest}.
 * This class extends {@link UserMapper} to inherit common user mapping functionalities.
 */
@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        uses = {Validator.class, UserMapper.class}
)
@Component
public abstract class CertifierMapper {
     /**
     * Maps a {@link User} to a {@link CertifierRequest}
     * @param user the user to map
     * @return a new {@link CertifierRequest} instance with the user's details
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "submittedAt", ignore = true)
    @Mapping(target = "processedAt", ignore = true)
    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "requestingUser", source = "user")
    @Mapping(target = "status", expression = "java(it.unicam.cs.ids.context.catalog.domain.model.ApprovalStatus.PENDING)")
    public abstract CertifierRequest fromUser(User user);

    public abstract CertifierRequestDTO toDto(CertifierRequest certifierRequest);
}
