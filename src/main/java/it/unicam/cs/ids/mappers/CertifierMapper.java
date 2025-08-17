package it.unicam.cs.ids.mappers;

import it.unicam.cs.ids.dto.CertifierRequestDTO;
import it.unicam.cs.ids.models.CertifierRequestEntity;
import it.unicam.cs.ids.models.User;
import it.unicam.cs.ids.shared.application.Validator;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

/**
 * Mapper for converting between {@link User} and {@link CertifierRequestEntity}.
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
     * Maps a {@link User} to a {@link CertifierRequestEntity}
     * @param user the user to map
     * @return a new {@link CertifierRequestEntity} instance with the user's details
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "submittedAt", ignore = true)
    @Mapping(target = "processedAt", ignore = true)
    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "requestingUser", source = "user")
    @Mapping(target = "status", expression = "java(it.unicam.cs.ids.shared.kernel.enums.ApprovalStatus.PENDING)")
    public abstract CertifierRequestEntity fromUser(User user);

    public abstract CertifierRequestDTO toDto(CertifierRequestEntity certifierRequest);
}
