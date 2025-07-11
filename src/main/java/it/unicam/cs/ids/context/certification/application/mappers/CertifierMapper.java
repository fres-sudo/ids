package it.unicam.cs.ids.context.certification.application.mappers;

import it.unicam.cs.ids.context.identity.application.mappers.UserMapper;
import it.unicam.cs.ids.context.certification.infrastructure.web.dtos.requests.CertifierRequest;
import it.unicam.cs.ids.context.identity.domain.model.User;
import it.unicam.cs.ids.context.certification.domain.repositories.CertifierRequestRepository;
import it.unicam.cs.ids.shared.application.Validator;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Mapper for converting between {@link User} and {@link CertifierRequest}.
 * This class extends {@link UserMapper} to inherit common user mapping functionalities.
 */
@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        uses = {Validator.class}
)
@Component
public abstract class CertifierMapper {
    @Autowired
    CertifierRequestRepository certifierRequestRepository;

    /**
     * Maps a {@link User} to a {@link CertifierRequest}
     * @param user the user to map
     * @return a new {@link CertifierRequest} instance with the user's details
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "requestDate", source = "user.createdAt")
    @Mapping(target = "status", expression = "java(it.unicam.cs.ids.context.catalog.domain.model.ApprovalStatus.PENDING)")
    public abstract CertifierRequest fromUser(User user);


}
